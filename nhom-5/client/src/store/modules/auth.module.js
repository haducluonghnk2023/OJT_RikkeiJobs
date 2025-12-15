import { getAllUsers, register, login } from "@/apis/auth/loginApi";

const auth = {
  namespaced: true, // Thêm namespace để rõ ràng hơn
  state: {
    users: [],
    currentUser: null,
  },
  getters: {
    getCurrentUser: (state) => state.currentUser,
    getAllUsers: (state) => state.users || [],
    getUserByUsername: (state) => (username) => {
      return state.users.find((user) => user.userName === username);
    },
    getUserByEmail: (state) => (email) => {
      return state.users.find((user) => user.email === email);
    },
  },
  mutations: {
    GET_USERS: (state, user) => {
      state.users = user;
    },

    SET_CURRENT_USER: (state, user) => {
      state.currentUser = user;
    },
  },
  actions: {
    registerUser: async ({ commit, state }, payload) => {
      try {
        // Server sẽ kiểm tra email và username trùng lặp
        const response = await register(payload);
        // extractResponseData đã extract data rồi, nên response là UserResponse object trực tiếp
        if (response) {
          commit("SET_CURRENT_USER", response);
        }
        return response;
      } catch (error) {
        console.error("Lỗi trong quá trình đăng kí tài khoản:", error);
        throw error;
      }
    },
    getAllUsers: async ({ commit }) => {
      try {
        const users = await getAllUsers();
        commit("GET_USERS", users);
        return users;
      } catch (error) {
        console.log(error);
      }
    },
    
    loginUser: async ({ commit }, credentials) => {
      try {
        const response = await login(credentials);
        // extractResponseData đã extract data rồi, nên response là UserResponse object trực tiếp
        if (response) {
          commit("SET_CURRENT_USER", response);
        }
        return response;
      } catch (error) {
        console.error("Lỗi trong quá trình đăng nhập:", error);
        throw error;
      }
    },
  },
};

export default auth;
