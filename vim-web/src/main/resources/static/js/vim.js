$(".js-flash-close").click(function () {
  $(this).closest(".flash").hide();
});

$(".js-toast-close").click(function () {
  $(this).closest(".Toast").remove();
});

$(".js-box-open").click(function () {
  addOverlay();
  const boxId = $(this).attr('data-box-id');
  $("#" + boxId).show();
});

$(document).on("click", ".js-box-close", function () {
  $(this).closest(".Box").hide();
  removeOverlay();
});

$(document).keyup(function (e) {
  if (e.key === "Escape") {
    const dialog = $(".dialog");
    if (dialog.length && dialog.hasClass("closable")) {
      dialog.hide();
      removeOverlay();
    }
  }
});

function removeOverlay() {
  const overlay = $("#overlay");
  if (overlay.length) {
    overlay.remove();
  }
}

function addOverlay() {
  let overlay = document.createElement("div");
  overlay.setAttribute("id", "overlay");
  $("body").prepend(overlay);
}

$('.reportDefect').click(function () {
  const url = $(this).attr("data-url");
  loadAndOpen(url, $("#boxDefectForm"));
});

function loadAndOpen(url, target, overlay = true) {
  if (overlay) {
    addOverlay();
  }
  target.load(url);
  target.show();
}