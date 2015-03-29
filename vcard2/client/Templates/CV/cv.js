Template.CV.helpers({
  cv: function() {
    return CV.find({});
  },
});

Template.CV.events({
  "click #cv .edit": function() {
    showEditForm("cv");
  },

  "submit .new-cv": function () {
    return updateEditForm("cv", CV);
  },
});
