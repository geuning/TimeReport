<!--
  변수명 변경 완료

  추가 사항 
  1. memberId를 추가 값에서는 넣어주기(addTask에서)
-->
<template>
  <div class="relative">
    <div class="modal bg-gray-700 rounded-lg absolute">
      <div class="modal-title bg-emerald-500 rounded-t-lg text-gray-200 flex">
        <div><p class="ml-3 mt-3">프로젝트 추가</p></div>
        <div class="grow"></div>
        <!--실제 모달창 닫는 버튼-->
        <button
          @click="this.$emit('closeModal')"
          style="width: 30px; height: 30px"
          class="hover:bg-rose-500 active:bg-rose-600 focus:outline-none rounded mr-5 mt-3"
        >
          <font-awesome-icon icon="fa-solid fa-xmark" />
        </button>
      </div>

      <div class="flex">
        <div class="text-gray-200 flex mr-8 mt-4 text-xs"></div>
      </div>
      <!-- 프로젝트 작성 란-->
    <div class="flex mr-7">
        <div class="grow"></div>
        <div>
            <button style="margin-right:200x" @click="SetData()" class="w-10 h-6 mt-4 text-gray-200 rounded-lg bg-sky-500 hover:bg-sky-600 active:bg-sky-700 focus:outline-none ">추가</button>
        </div>
    </div>    
      <div class="text-gray-200">
        <div class="mt-4 perf-box rounded">
          <div class="mb-2">
            <div style="margin-bottom: 15px;" class="flex">
                <p style="padding: 10px;">프로젝트 코드 : </p>
                <input v-model="projectCode" type="text" name="project-code" placeholder="프로젝트 코드" style="height:30px; width: 350px;" class="mt-1.5 ml-5 focus:ring-indigo-500 focus:border-indigo-500 block pl-7  sm:text-sm border-gray-300 rounded-md"/>
            </div>
            <div style="margin-bottom: 15px; " class="flex">
                <p style="padding: 10px; ">프로젝트 명 : </p>
                <input v-model="projectNm" type="text" name="project-code" placeholder="프로젝트 명" style="height:30px; width: 500px;" class="mt-1.5 ml-9 first-letter:ml-4 focus:ring-indigo-500 focus:border-indigo-500 block pl-7  sm:text-sm border-gray-300 rounded-md"/>
            </div>
            <div style="margin-bottom: 15px;" class="flex">
                <p style="padding: 10px;"> PJ 시작일 : </p>
                <input v-model="startedAt" type="date" name="project-code" style="margin-left: 49px; height: 30px;" class=" focus:ring-indigo-500 focus:border-indigo-500 block pl-7  sm:text-sm border-gray-300 rounded-md"/>
            </div>
            <div style="margin-bottom: 15px;" class="flex">
                <p style="padding: 10px;"> PJ 종료일 : </p>
                <input v-model="endedAt" type="date" name="project-code" style="margin-left: 49px; height: 30px;" class="focus:ring-indigo-500 focus:border-indigo-500 block pl-7  sm:text-sm border-gray-300 rounded-md"/>
            </div>
            <div style="margin-bottom: 15px;" class="flex">
                <p style="padding: 10px;">프로젝트 매니저 : </p>
                <input v-model="projectManager" type="text" name="project-code" style="height:30px; width: 350px;" placeholder="프로젝트 매니저" class="mt-1.5 ml-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7  sm:text-sm border-gray-300 rounded-md"/>
            </div>
          </div>
            <!-- <p>{{ PM }}</p>
            <p>{{ PjStart }}</p> -->
          <!-- 업무 반복 구현 끝-->
      
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import taskType from "../assets/task.json";
import project from "../assets/project.json";
import { library } from "@fortawesome/fontawesome-svg-core";

/* add some free styles */
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

/* add each imported icon to the library */
library.add(faXmark, faPlus);
import axios from "axios";
export default {
  name: "AdminProjectEdit",
  props: {
    oneDayInfo: Object,
  },
  async mounted() {},
  data() {
      return {
          sendProjectData: {
              projectCode: "",
              projectNm: "",
              startedAt: "",
              endedAt: "",
            projectManager:"",
        },
        projectCode: "",
        projectNm: "",
        startedAt: "",
          endedAt: "",
        projectManager:"",
    };
  },
  computed: {},
    methods: {
    async SetData() {
        this.startedAt = this.startedAt.replace(/\-/g, "");
        this.endedAt = this.endedAt.replace(/\-/g, "");
            this.sendProjectData.projectCode = this.projectCode;
            this.sendProjectData.projectNm = this.projectNm;
            this.sendProjectData.startedAt = this.startedAt;
            this.sendProjectData.endedAt = this.endedAt;
            this.sendProjectData.projectManager = this.projectManager;

        console.log(this.sendProjectData)

        if (confirm('프로젝트를 추가하시겠습니까?') === true) {
            await axios.post('/api/projects/admin/register', this.sendProjectData, { withCredentials: true })
                .then((res) => {
                    if (res.data.code === 1000) {
                        alert('프로젝트를 추가하였습니다.');
                        this.$router.go(0);
                    } else {
                        alert(res.data.message);
                        this.logout();
                    }
                })
                .catch((res) => {
                    alert(res)
                })
        } else {
            return
        }

        
        },
    logout(){
      localStorage.setItem('memberId', '0')
      localStorage.setItem('memberNm','No');
      localStorage.setItem('grade','GEUST');
      localStorage.setItem('deptNm', '무소속');
      this.$router.push('/')
    },
  },
};
</script>

<style>
.modal {
  width: 1200px;
  height: 500px;
  left: 300px;
  top: 100px;
  z-index: 1;
  overflow-y: auto;
}
.modal::-webkit-scrollbar {
  display: none;
}

.modal-title {
  width: 100%;
  height: 50px;
}
.modal-total-time {
  width: 1150px;
  height: 80px;
  margin: auto;
}
.modal-content {
  margin: auto;
  width: 1150px;
  height: 100px;
}
.select-option {
  color: black;
  width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
}
.perf-box {
  width: 1150px;
  margin: 10px 20px 0 23px;
  padding: 10px;
  background-color: rgb(75 85 99);
}
.project-data{
    height: 30px;
}
</style>
