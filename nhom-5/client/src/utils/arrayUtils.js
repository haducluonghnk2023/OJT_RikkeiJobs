/**
 * Shuffle array using Fisher-Yates algorithm
 * @param {Array} array - Array to shuffle
 * @returns {Array} - Shuffled array
 */
export const shuffleArray = (array) => {
  const shuffled = [...array]; // Create a copy to avoid mutating original
  for (let i = shuffled.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
  }
  return shuffled;
};

/**
 * Get distinct values from array
 * @param {Array} array - Array to get distinct values from
 * @param {string} key - Key to extract distinct values (optional)
 * @returns {Array} - Array of distinct values
 */
export const getDistinctValues = (array, key = null) => {
  if (!key) {
    return [...new Set(array)];
  }
  return [...new Set(array.map((item) => item[key]))];
};

