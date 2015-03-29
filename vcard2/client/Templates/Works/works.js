Template.Works.helpers({
  works: function() {
    return Works.find({});
  },
});

Template.Works.events({
  "click #works .edit": function() {
    showEditForm("works");
  },

  "submit .new-works": function () {
    return updateEditForm("works", Works);
  },
});
