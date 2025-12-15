import { createRouter, createWebHistory } from "vue-router";

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
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
