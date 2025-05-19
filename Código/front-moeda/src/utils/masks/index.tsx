export const cpfMask = (value: string): string => {
  if (!value) return value;

  value = value.replace(/\D/g, '');

  if (value.length > 11) {
    value = value.slice(0, 11);
  }

  value = value.replace(/(\d{3})(\d)/, '$1.$2');
  value = value.replace(/(\d{3})(\d)/, '$1.$2');
  value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');

  return value;
};

export const phoneMask = (value: string): string => {
  if (!value) return value;

  value = value.replace(/\D/g, '');

  if (value.length > 11) {
    value = value.slice(0, 11);
  }

  value = value.replace(/(\d{2})(\d)/, '($1) $2');
  value = value.replace(/(\d{5})(\d)/, '$1-$2');

  return value;
};

export const rgMask = (value: string): string => {
  if (!value) return value;

  let letters = value.slice(0, 3).replace(/[^a-zA-Z]/g, '').toUpperCase();
  let numbers = value.slice(2).replace(/\D/g, '');

  if (letters.length > 2) {
    letters = letters.slice(0, 2);
  }

  if (numbers.length > 8) {
    numbers = numbers.slice(0, 8);
  }

  numbers = numbers.replace(/(\d{2})(\d)/, '$1.$2');
  numbers = numbers.replace(/(\d{3})(\d)/, '$1.$2');

  return letters ? `${letters}-${numbers}` : numbers;
};
