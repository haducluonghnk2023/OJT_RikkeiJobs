import { getUserById } from "@/apis/userApi";

const extractTokenFromRoute = (to) => {
  // Support /admin?token=... and /auth?token=...
  const tokenFromQuery = to?.query?.token;
  if (tokenFromQuery !== undefined && tokenFromQuery !== null && String(tokenFromQuery).trim() !== "") {
    return String(tokenFromQuery).trim();
  }

  // Fallback: support #/admin?token=... style hashes if someone uses hash mode
  const hash = typeof window !== "undefined" ? window.location.hash : "";
  if (hash && hash.includes("token=")) {
    const qs = hash.includes("?") ? hash.split("?")[1] : "";
    const params = new URLSearchParams(qs);
    const t = params.get("token");
    if (t && t.trim() !== "") return t.trim();
  }

  return null;
};

const persistTokenFromRouteIfAny = (to) => {
  const incoming = extractTokenFromRoute(to);
  if (!incoming) return null;

  localStorage.setItem("token", incoming);

  // Clean token from URL to avoid leaking it in screenshots/logs/history.
  try {
    const url = new URL(window.location.href);
    url.searchParams.delete("token");
    window.history.replaceState({}, "", url.toString());
  } catch {
    // ignore
  }

  return incoming;
};

/**
 * Guard để kiểm tra quyền admin
 * @param {Object} to - Route object
 * @param {Object} from - Route object
 * @param {Function} next - Next function
 */
export const adminGuard = async (to, from, next) => {
  // Allow SSO-style token handoff from client (5173) -> admin (5174)
  persistTokenFromRouteIfAny(to);

  const token = localStorage.getItem("token");

  // Nếu không có token, chuyển đến trang login
  if (!token) {
    next("/auth");
    return;
  }

  try {
    // Lấy thông tin user từ token (token là user ID)
    // Convert token sang number nếu cần (vì localStorage lưu string)
    const userId = token ? (isNaN(token) ? token : Number(token)) : null;
    const user = await getUserById(userId);

    // Kiểm tra role - normalize để tránh vấn đề case sensitivity hoặc khoảng trắng
    const userRole = user?.role?.toLowerCase()?.trim();
    if (user && userRole === "admin") {
      // Cho phép truy cập
      next();
    } else {
      // Log để debug
      console.warn("User role check failed:", {
        token,
        userId: user?.id,
        role: user?.role,
        normalizedRole: userRole,
        userExists: !!user,
      });
      // Không phải admin, chuyển đến trang 403
      next("/403");
    }
  } catch (error) {
    console.error("Error checking admin role:", error);
    console.error("Error details:", {
      message: error.message,
      response: error.response?.data,
      status: error.response?.status,
    });
    // Nếu có lỗi (token không hợp lệ, user không tồn tại, etc.), chuyển đến login
    localStorage.removeItem("token");
    next("/auth");
  }
};

/**
 * Guard để kiểm tra đã đăng nhập (cho trang login)
 * @param {Object} to - Route object
 * @param {Object} from - Route object
 * @param {Function} next - Next function
 */
export const authGuard = (to, from, next) => {
  // If token is passed on /auth?token=..., persist it then go straight to /admin
  persistTokenFromRouteIfAny(to);
  const token = localStorage.getItem("token");

  // Nếu đã có token, chuyển đến trang admin
  if (token) {
    next("/admin");
  } else {
    next();
  }
};

