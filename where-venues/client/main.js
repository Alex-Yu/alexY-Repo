  Session.setDefault("venues", "");
  Session.setDefault("countVenues", 0);

  const DEFAULT_LATITUDE = 35.673343;   //Tokyo
  const DEFAULT_LONGITUDE = 139.710388; //Tokyo
  const DEFAULT_ZOOM = 10;

  var map;
  var markers = [];
  var latitude = DEFAULT_LATITUDE;
  var longitude = DEFAULT_LONGITUDE;
  var radius = 0;

  var removeMarkers = function() {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    markers = [];
  };

  var panMapToDefault = function() {
    if (map) {
      map.panTo(new google.maps.LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)); //to Tokyo
      map.setZoom(DEFAULT_ZOOM);
    }
  }


  var getVenues = function(query, ll, radius) {
    var clientId = "WHQ2BVWLLW2ZQLLUSPGLGDFQUWMJX14XIMZ0TU44XETKHOW3";
    var clientSecret = "CO4NJDSXAIHUT3ZWPAYZPI0UZUY0ILL0ZPRZGVQEEF2V4R21";

    $.getJSON("https://api.foursquare.com/v2/venues/search?ll=" + ll
              + "&radius=" + radius
              + "&query=" + query
              + "&client_id=" + clientId
              + "&client_secret=" + clientSecret
              + "&v=20150320", //version of API
              function(data) {
                Session.set("venues", data.response.venues);
                Session.set("countVenues", data.response.venues.length);

                //remove previously markers from map
                removeMarkers();

                //add new markers to map
                var venues = data.response.venues;
                for (i = 0; i < venues.length; i++) {
                  var latlng = new google.maps.LatLng(venues[i].location.lat, venues[i].location.lng);
                  var marker = new google.maps.Marker({
                    position: latlng,
                    map: map,
                  });
                  markers.push(marker);
                }
              });
  }


  //calculate radius of current view
  var getRadius = function () {
    if (map) {
      var x1 = map.getBounds().getSouthWest().lat();
      var y1 = map.getBounds().getSouthWest().lng();
      var x2 = map.getBounds().getNorthEast().lat();
      var y2 = map.getBounds().getNorthEast().lng();

      var x1y1 = new google.maps.LatLng(x1, y1);
      var x2y1 = new google.maps.LatLng(x2, y1);
      var xDistance = google.maps.geometry.spherical.computeDistanceBetween(x1y1, x2y1);
      var x1y1 = new google.maps.LatLng(x1, y1);
      var x1y2 = new google.maps.LatLng(x1, y2);
      var yDistance = google.maps.geometry.spherical.computeDistanceBetween(x1y1, x1y2);

      return Math.min(xDistance, yDistance) / 2;
    }
  }


  Template.body.onRendered(
    //map init
    function() {
      var myLatlng = new google.maps.LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
      var myOptions = {
        zoom: DEFAULT_ZOOM,
        center: myLatlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      };

      //load map
      map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);


      //getting new ll when panning
      google.maps.event.addListener(map, 'center_changed', function() {
        latitude = map.getCenter().lat();
        longitude = map.getCenter().lng();
      });

      //getting new radius when init and zooming
      google.maps.event.addListener(map, 'bounds_changed', function() {
        radius = getRadius();
     });

    }
  );

  Tracker.autorun(function(){
    var onUserChange = function() {
      Session.set("venues", null);
      Session.set("countVenues", 0);
      removeMarkers();
      panMapToDefault();
    }

    if(Meteor.user()){
      // login handler
      onUserChange();
    }
    else{
      // logout handler
      onUserChange();
    }
  });

  Template.VenuesList.helpers({
      venues : function() {
        return Session.get("venues");
      },
  });

  Template.QueriesList.helpers({
      queries : function() {
        return Queries.find({owner: Meteor.userId()});
      }
  });

  Template.VenuesTitle.helpers({
    countVenues: function() {
      return Session.get("countVenues");
    },
  });

  Template.body.events({
    "submit .new-query": function(event) {
      var query = event.target.text.value;
      var ll = latitude + "," + longitude;

      //get result set in venues array
      getVenues(query, ll, radius);

      //if user logged in then insert query to database
      if (Meteor.userId() != null) {
        var now = new Date();
        var date = now.toDateString();
        var time = now.toTimeString();
        Queries.insert({
          owner: Meteor.userId(),
          text: query,
          latitude: latitude,
          longitude: longitude,
          radius: (radius/ 1000).toFixed(0),
          dateTime: date.substring(date.indexOf(" ") + 1, date.lastIndexOf(" "))
                      + " " + time.substring(0, time.lastIndexOf(":")),
        });
      }
      // Clear form
      event.target.text.value = "";

      // Prevent default form submit
      return false;
    },

    "click #csv-button": function() {
      var venues = Session.get('venues');
      if (venues) {
        csvData = "Name; City; Street Address; Latitude; Longitude%0A";
        for(var i = 0; i < venues.length; i++)
        csvData += venues[i].name +"; "
                    + venues[i].location.city + "; "
                    + venues[i].location.address + "; "
                    + venues[i].location.lat + "; "
                    + venues[i].location.lng + "%0A";
        var link = document.createElement('a');
        link.href = "data:text/csv;charset=utf-8," + csvData;
        link.download = 'venues.csv';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    }
  });

  Template.Query.events({
    "click .delete": function () {
    Queries.remove(this._id);
    },
  });
