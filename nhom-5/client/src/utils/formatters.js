export function formatSalary(salary, salaryCurrent) {
  const s = (salary ?? "").toString().trim();
  const c = (salaryCurrent ?? "").toString().trim();

  if (!s && !c) return "";
  if (!s) return c;
  if (!c) return s;

  const sLower = s.toLowerCase();
  const cLower = c.toLowerCase();
  const hasUnit = (v) => /[a-zA-Z₫$€]|vnđ|vnd|usd|eur|triệu|tr|k|m/i.test(v);

  if (sLower.includes(cLower)) return s;

  if (sLower.includes(cLower.replace(/\s+/g, ""))) return s;

  // Nếu salary đã có đơn vị đầy đủ, ưu tiên hiển thị salary để tránh trùng "30-50M 30M"
  if (hasUnit(s) && hasUnit(c)) return s;

  const salaryLooksNumeric = /^[0-9\s.\-–~]+$/.test(s);
  const currentLooksUnit = /[a-zA-Z₫$€]|vnđ|vnd|usd|eur|m|triệu/i.test(c);
  if (salaryLooksNumeric && currentLooksUnit) {
    return `${s} ${c}`;
  }

  return `${s} ${c}`;
}
