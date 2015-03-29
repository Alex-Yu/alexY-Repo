Template.About.helpers({
  about: function() {
    return About.find({});
  },
});

Template.About.events({

  "click #about .edit": function() {
    showEditForm("about");
  },

  "submit .new-about": function() {
    return updateEditForm("about", About);
  },

});
