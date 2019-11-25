$(".js-flash-close").click(function (e) {
  $(this).closest(".flash").hide();
});

$(".js-box-close").click(function (e) {
  $(this).closest(".Box").hide();
  removeOverlay();
});

function removeOverlay() {
  $('#overlay').remove();
}

function addOverlay() {
  var overlay = document.createElement("div");
  overlay.setAttribute("id", "overlay");
  $('body').prepend(overlay);
}