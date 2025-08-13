const pickupInput = document.getElementById('pickupDate');
const dropInput = document.getElementById('dropDate');
const vehicleSelect = document.getElementById('vehicle');
const totalRentInput = document.getElementById('totalRent');

function calculateDays(pickup, drop) {
  const pickupDate = new Date(pickup);
  const dropDate = new Date(drop);
  const timeDiff = dropDate - pickupDate;
  return Math.max(0, Math.ceil(timeDiff / (1000 * 60 * 60 * 24)));
}

function updateRent() {
  const pickup = pickupInput.value;
  const drop = dropInput.value;
  const rate = parseInt(vehicleSelect.value);

  if (pickup && drop) {
    const days = calculateDays(pickup, drop);
    totalRentInput.value = days > 0 ? `$${days * rate}` : '$0';
  }
}

pickupInput.addEventListener('change', updateRent);
dropInput.addEventListener('change', updateRent);
vehicleSelect.addEventListener('change', updateRent);
