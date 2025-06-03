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

  // Separa letras e números
  let letters = value.slice(0, 3).replace(/[^a-zA-Z]/g, '').toUpperCase();
  let numbers = value.slice(2).replace(/\D/g, '');

  // Limita a 2 letras e 8 dígitos
  letters = letters.slice(0, 2);
  numbers = numbers.slice(0, 8);

  // Aplica a máscara: 12.345.678 → 12.345.678
  if (numbers.length > 2) {
    numbers = numbers.replace(/^(\d{2})(\d)/, '$1.$2');
  }
  if (numbers.length > 6) {
    numbers = numbers.replace(/^(\d{2}\.\d{3})(\d)/, '$1.$2');
  }

  // Retorna com UF + traço
  return letters ? `${letters}-${numbers}` : numbers;
};
