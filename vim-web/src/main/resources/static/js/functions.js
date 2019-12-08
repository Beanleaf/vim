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

function loadAndOpen(url, target, overlay = true) {
  if (overlay) {
    addOverlay();
  }
  target.load(url);
  target.show();
}

function formatNumber(n, divider = ",") {
  // format number 1000000 to 1,234,567
  return n.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, divider)
}

function formatDecimal(input, blur, divider = ",") {
  const decimalChar = divider === "." ? "," : ".";
  // appends $ to value, validates decimal side
  // and puts cursor back in right position.

  // get input value
  let input_val = input.val();

  // don't validate empty input
  if (input_val === "") {
    return;
  }

  // original length
  const original_len = input_val.length;

  // initial caret position
  let caret_pos = input.prop("selectionStart");

  // check for decimal
  if (input_val.indexOf(decimalChar) >= 0) {
    // get position of first decimal
    // this prevents multiple decimals from
    // being entered
    const decimal_pos = input_val.indexOf(decimalChar);

    // split number by decimal point
    let left_side = input_val.substring(0, decimal_pos);
    let right_side = input_val.substring(decimal_pos);

    // add commas to left side of number
    left_side = formatNumber(left_side, divider);

    // validate right side
    right_side = formatNumber(right_side, divider);

    // On blur make sure 2 numbers after decimal
    if (blur) {
      right_side += "00";
    }

    // Limit decimal to only 2 digits
    right_side = right_side.substring(0, 2);

    // join number by .
    input_val = left_side + decimalChar + right_side;

  } else {
    // no decimal entered
    // add commas to number
    // remove all non-digits
    input_val = formatNumber(input_val, divider);

    // final formatting
    if (blur) {
      input_val += decimalChar + "00";
    }
  }

  // send updated string to input
  input.val(input_val);

  // put caret back in the right position
  const updated_len = input_val.length;
  caret_pos = updated_len - original_len + caret_pos;
  input[0].setSelectionRange(caret_pos, caret_pos);
}