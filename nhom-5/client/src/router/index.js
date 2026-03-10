import { createRouter, createWebHistory } from "vue-router";
import store from "@/store/store";
import { message } from "ant-design-vue";

const routes = [
  {
    path: "/login",
    component: () =>
      import(/* webpackChunkName: "Login" */ "@/views/auth/Login.vue"),
  },
  {
    path: "/register",
    component: () =>
      import(/* webpackChunkName: "Register" */ "@/views/auth/Register.vue"),
  },
  {
    path: "/homepage",
    component: () =>
      import(/* webpackChunkName: "HomePage" */ "@/views/home/HomePage.vue"),
    alias: ["/home-page", "/home", "/"],
  },
  {
    path: "/homepage/candidate",
    component: () =>
      import(
        /* webpackChunkName: "Candidate" */ "@/views/candidate/Candidate.vue"
      ),
  },
  {
    path: "/homepage/candidate/candidateDetail/:id",
    component: () =>
      import(
        /* webpackChunkName: "candidateDetail" */ "@/views/candidate/CandidateDetail.vue"
      ),
  },
  {
    path: "/homepage/listJob",
    name: "listJob",
    component: () =>
      import(/* webpackChunkName: "listJob" */ "@/views/job/JobList.vue"),
  },
  {
    path: "/homepage/saveJob",
    name: "saveJob",
    component: () =>
      import(/* webpackChunkName: "saveJob" */ "@/views/job/SaveJob.vue"),
  },
  {
    path: "/homepage/listJob/jobDetail/:id",
    component: () =>
      import(/* webpackChunkName: "jobDetail" */ "@/views/job/JobDetail.vue"),
  },
  {
    path: "/profile",
    component: () =>
      import(
        /* webpackChunkName: "profile" */ "@/views/user/userProfile/MainLayout.vue"
      ),
    children: [
      {
        path: "information",
        component: () =>
          import(
            /* webpackChunkName: "profile" */ "@/views/user/userProfile/InformationView.vue"
          ),
      },
      {
        path: "cv",
        component: () =>
          import(
            /* webpackChunkName: "cv" */ "@/views/user/userProfile/CvView.vue"
          ),
      },
      {
        path: "certificate",
        component: () =>
          import(
            /* webpackChunkName: "cv" */ "@/views/user/userProfile/CertificateView.vue"
          ),
      },
      {
        path: "interview",
        component: () =>
          import(
            /* webpackChunkName: "interview" */ "@/views/user/userProfile/InterviewView.vue"
          ),
      },
      {
        path: "enterprise-management",
        name: "enterprise-management",
        component: () =>
          import(
            /* webpackChunkName: "enterpriseManagement" */ "@/views/enterprise/EnterpriseManagement.vue"
          ),
      },
    ],
  },
  {
    path: "/enterprise-dashboard",
    name: "enterprise-dashboard",
    component: () =>
      import(
        /* webpackChunkName: "enterpriseDashboard" */ "@/views/enterprise/MainLayout.vue"
      ),
    children: [],
  },
  {
    path: "/company/:id/dashboard",
    component: () =>
      import(
        /* webpackChunkName: "CompanyPage" */ "@/views/enterprise/company/Sidebar.vue"
      ),
    children: [
      {
        path: "detail",
        name: "company-detail",
        component: () =>
          import(
            /* webpackChunkName: "companyDetail" */ "@/views/enterprise/company/EnterpriseDetail.vue"
          ),
      },
      {
        path: "job",
        name: "company-job",
        component: () =>
          import(
            /* webpackChunkName: "companyDetail" */ "@/views/enterprise/company/EnterpriseJobManager.vue"
          ),
      },
      {
        path: "interview-booking",
        name: "company-interview-booking",
        component: () =>
          import(
            /* webpackChunkName: "conpanyInterviewBooking" */ "@/views/enterprise/company/EnterpriseInterviewBooking.vue"
          ),
      },
    ],
  },
  {
    path: "/about-us",
    component: () =>
      import(/* webpackChunkName: "AboutUs" */ "@/layout/AboutUs.vue"),
  },
  {
    path: "/contact",
    name: "contact",
    component: () =>
      import(/* webpackChunkName: "Contact" */ "@/views/contact/ContactView.vue"),
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/",
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    }
    if (to.hash) {
      return { el: to.hash, behavior: "smooth", top: 0 };
    }
    return { top: 0, left: 0 };
  },
});

// Chặn user (role user) chưa tải CV xem trang ứng viên - chỉ được xem việc làm
router.beforeEach(async (to, from, next) => {
  const isCandidateRoute =
    to.path === "/homepage/candidate" ||
    to.path.startsWith("/homepage/candidate/candidateDetail/");
  if (!isCandidateRoute) return next();

  const tokenRaw = localStorage.getItem("token");
  const userId = tokenRaw ? JSON.parse(tokenRaw) : null;
  if (!userId) return next();

  const u = store.state.user?.userLogin;
  if (!u) {
    await store.dispatch("getUser", userId);
  }
  const user = store.state.user?.userLogin;
  if (!user || user.role !== "user") return next();

  if (!store.getters.hasCv) {
    await store.dispatch("getCv");
  }
  if (!store.getters.hasCv) {
    message.warning("Bạn cần tải CV lên để xem thông tin ứng viên.");
    return next({ path: "/homepage/listJob", replace: true });
  }
  next();
});

export default router;
