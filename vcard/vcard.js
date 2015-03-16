About = new Mongo.Collection("aboutDB");
CV = new Mongo.Collection("cvDB");
Works = new Mongo.Collection("worksDB");
Contacts = new Mongo.Collection("contactsDB");


if (Meteor.isClient) {

  Template.body.helpers({
    myPhoto: "/morda.jpg",
    about: function() {
      return About.find({});
    },
    cv: function() {
      return CV.find({});
    },
    works: function() {
      return Works.find({});
    },
    contacts: function() {
      return Contacts.find({});
    },
  });

  var showEditForm = function(pageName) {
    document.getElementsByClassName("new-" + pageName)[0].classList.remove("hide");
  }

  var updateEditForm = function (pageName, collection) {
    // This function is called when the new about form is submitted
    var text = document.getElementById("new-" + pageName + "-text").value;
    if (collection.find({}).count() === 0) {
      collection.insert({text: text});
    } else {
      var record = collection.findOne({});
      collection.update({_id: record._id}, {$set: {text: text}});
    }

    //hide textarea
    document.getElementsByClassName("new-" + pageName)[0].classList.add("hide");

//          document.getElementById("new-about-text").value = About.findOne({}).text;

  // Prevent default form submit
    return false;
 };

  Template.body.events({
      "click .nav ul li": function(e){
        //hide old page
        document.getElementsByClassName("visible")[0].setAttribute("class", "hide");

        //show new page
        switch (e.target.getAttribute("class")) {
          case("about-menu"):
            document.getElementById("about").setAttribute("class", "visible");
            break;
          case("cv-menu"):
            document.getElementById("cv").setAttribute("class", "visible");
            break;
          case("works-menu"):
            document.getElementById("works").setAttribute("class", "visible");
            break;
          case("contacts-menu"):
            document.getElementById("contacts").setAttribute("class", "visible");
            break;

        };
      },
        "click #about .edit": function() {
          showEditForm("about");
        },

        "submit .new-about": function() {
          return updateEditForm("about", About);
        },

        "click #cv .edit": function() {
          showEditForm("cv");
        },

        "submit .new-cv": function () {
          return updateEditForm("cv", CV);
        },

        "click #works .edit": function() {
          showEditForm("works");
        },

        "submit .new-works": function () {
          return updateEditForm("works", Works);
        },
        "click #contacts .edit": function() {
          showEditForm("contacts");
        },

        "submit .new-contacts": function () {
          return updateEditForm("contacts", Contacts);
        },


  });
}
if (Meteor.isServer) {
  Meteor.startup(function () {
    // code to run on server at startup
  });
}
