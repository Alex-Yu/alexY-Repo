Template.Contacts.helpers({
  contacts: function() {
    return Contacts.find({});
  },
});

Template.Contacts.events({
  "click #contacts .edit": function() {
    showEditForm("contacts");
  },

  "submit .new-contacts": function () {
    return updateEditForm("contacts", Contacts);
  },
});
