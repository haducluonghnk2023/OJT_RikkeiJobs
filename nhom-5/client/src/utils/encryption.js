import CryptoJS from 'crypto-js';

// Secret key cho mã hóa (nên lưu trong biến môi trường trong production)
const SECRET_KEY = 'rikkei-jobs-secret-key-2024';

/**
 * Mã hóa dữ liệu bằng AES
 * @param {string} data - Dữ liệu cần mã hóa
 * @returns {string} - Dữ liệu đã được mã hóa
 */
export const encryptData = (data) => {
  if (!data) return '';
  try {
    const encrypted = CryptoJS.AES.encrypt(data, SECRET_KEY).toString();
    return encrypted;
  } catch (error) {
    console.error('Lỗi khi mã hóa dữ liệu:', error);
    return '';
  }
};

/**
 * Giải mã dữ liệu đã được mã hóa bằng AES
 * @param {string} encryptedData - Dữ liệu đã được mã hóa
 * @returns {string} - Dữ liệu đã được giải mã
 */
export const decryptData = (encryptedData) => {
  if (!encryptedData) return '';
  try {
    const decrypted = CryptoJS.AES.decrypt(encryptedData, SECRET_KEY);
    const decryptedString = decrypted.toString(CryptoJS.enc.Utf8);
    return decryptedString;
  } catch (error) {
    console.error('Lỗi khi giải mã dữ liệu:', error);
    return '';
  }
};

