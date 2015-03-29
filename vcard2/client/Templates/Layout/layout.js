Template.Layout.events({
  "click .about-menu": function(e) {

    Router.go("About");
  },

  "click .cv-menu": function(e) {

    Router.go("CV");
  },

  "click .works-menu": function(e) {

    Router.go("Works");
  },

  "click .contacts-menu": function(e) {

    Router.go("Contacts");
  },
});
