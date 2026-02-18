export function formatSalary(salary, salaryCurrent) {
  const s = (salary ?? "").toString().trim();
  const c = (salaryCurrent ?? "").toString().trim();

  if (!s && !c) return "";
  if (!s) return c;
  if (!c) return s;

  const sLower = s.toLowerCase();
  const cLower = c.toLowerCase();

  if (sLower.includes(cLower)) return s;

  if (sLower.includes(cLower.replace(/\s+/g, ""))) return s;

  const salaryLooksNumeric = /^[0-9\s.\-–~]+$/.test(s);
  const currentLooksUnit = /[a-zA-Z₫$€]|vnđ|vnd|usd|eur|m|triệu/i.test(c);
  if (salaryLooksNumeric && currentLooksUnit) {
    return `${s}${c}`;
  }

  return `${s} ${c}`;
}
