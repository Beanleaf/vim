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

$(document).on("click", ".disabled", function (e) {
  e.preventDefault();
  e.stopPropagation();
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

$('.reportDefect').click(function () {
  const url = $(this).attr("data-url");
  loadAndOpen(url, $("#boxDefectForm"));
});

$("input[data-type='currency']").on({
  keyup: function () {
    formatDecimal($(this), false, $(this).attr("data-currency-divider"));
  },
  blur: function () {
    formatDecimal($(this), true, $(this).attr("data-currency-divider"));
  }
});
