Router.configure({
  layoutTemplate: 'Layout',
});

Router.route("About", {path: "/", name: "About"});
Router.route("CV", {path: "/cv", name: "CV"});
Router.route("Works", {path: "/works", name: "Works"});
Router.route("Contacts", {path: "/contacts", name: "Contacts"});


showEditForm = function(pageName) {
  document.getElementsByClassName("new-" + pageName)[0].classList.remove("hide");
}

updateEditForm = function (pageName, collection) {
  // This function is called when the edit-form is submitted
  var text = document.getElementById("new-" + pageName + "-text").value;
  if (collection.find({}).count() === 0) {
    collection.insert({text: text});
  } else {
    var record = collection.findOne({});
    collection.update({_id: record._id}, {$set: {text: text}});
  }

  //hide textarea
  document.getElementsByClassName("new-" + pageName)[0].classList.add("hide");

  //Prevent default form submit
  return false;
};
