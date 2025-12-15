import App from "@/App.vue";
import CertificateView from "@/view/certificateView.vue";
import CvView from "@/view/cvView.vue";
import EnterpriseView from "@/view/enterpriseView.vue";
import Home from "@/view/home.vue";
import InterviewBooking from "@/view/interviewBooking.vue";
import LanguageView from "@/view/languageView.vue";
import Login from "@/view/login.vue";
import UserView from "@/view/userView.vue";
import Forbidden403 from "@/view/Forbidden403.vue";

import { createRouter, createWebHistory } from "vue-router";
import JobView from "@/view/jobView.vue";
import { adminGuard, authGuard } from "./guards";

const routes = [
  {
    path: "/admin",
    component: Home,
    alias: ["/"],
    beforeEnter: adminGuard, // Bảo vệ route admin
    children: [
      {
        path: "user",
        component: UserView,
      },
      {
        path: "cv",
        component: CvView,
      },
      {
        path: "certificate",
        component: CertificateView,
      },
      {
        path: "enterprise",
        component: EnterpriseView,
      },
      {
        path: "language",
        component: LanguageView,
      },
      {
        path: "job",
        component: JobView,
      },
      {
        path: "interview",
        component: InterviewBooking,
      },
    ],
  },
  {
    path: "/auth",
    component: Login,
    beforeEnter: authGuard, // Nếu đã đăng nhập thì redirect về admin
  },
  {
    path: "/403",
    component: Forbidden403,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
