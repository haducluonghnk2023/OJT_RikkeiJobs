import CryptoJS from "crypto-js";

const SECRET_KEY = "rikkei-jobs-secret-key-2024";

export const encryptData = (data) => {
  if (!data) return "";
  try {
    const encrypted = CryptoJS.AES.encrypt(data, SECRET_KEY).toString();
    return encrypted;
  } catch (error) {
    console.error("Lỗi khi mã hóa dữ liệu:", error);
    return "";
  }
};

export const decryptData = (encryptedData) => {
  if (!encryptedData) return "";
  try {
    const decrypted = CryptoJS.AES.decrypt(encryptedData, SECRET_KEY);
    const decryptedString = decrypted.toString(CryptoJS.enc.Utf8);
    return decryptedString;
  } catch (error) {
    console.error("Lỗi khi giải mã dữ liệu:", error);
    return "";
  }
};
