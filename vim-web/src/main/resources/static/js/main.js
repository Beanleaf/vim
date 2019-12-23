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
$("#deleteTrigger").click(function () {
  $("#deleteConfirm").show();
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

$('.openBoxForm').click(function () {
  const url = $(this).attr("data-url");
  loadAndOpen(url, $("#boxForm"));
});

$("input[data-type='currency']").on({
  keyup: function () {
    formatDecimal($(this), false, $(this).attr("data-currency-divider"));
  },
  blur: function () {
    formatDecimal($(this), true, $(this).attr("data-currency-divider"));
  }
});

const debounceDelay = 500;
const successClass = "successful";
const errorClass = "errored";

const emailCheckUrl = "/admin/user/emailCheck";
$(".emailCheck").on({
  keyup: debounce(function () {
    checkUserData(emailCheckUrl,
        $(this), $("#userId"),
        successClass, errorClass)
  }, debounceDelay),
  blur: function () {
    checkUserData(emailCheckUrl,
        $(this), $("#userId"),
        successClass, errorClass)
  }
});
const usernameCheckUrl = "/admin/user/usernameCheck";
$(".usernameCheck").on({
  keyup: debounce(function () {
    checkUserData(usernameCheckUrl,
        $(this), $("#userId"),
        successClass, errorClass)
  }, debounceDelay),
  blur: function () {
    checkUserData(usernameCheckUrl,
        $(this), $("#userId"),
        successClass, errorClass)
  }
});