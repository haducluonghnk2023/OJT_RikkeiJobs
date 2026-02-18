export const shuffleArray = (array) => {
  const shuffled = [...array];
  for (let i = shuffled.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
  }
  return shuffled;
};

export const getDistinctValues = (array, key = null) => {
  if (!key) {
    return [...new Set(array)];
  }
  return [...new Set(array.map((item) => item[key]))];
};
