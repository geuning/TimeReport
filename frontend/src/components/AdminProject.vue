<template>
      <!--검색 부분 -->
      <AdminProjectEdit style="position:absolute" class="left-1" v-if="modal" @closeModal="addBtn"/>
     <div class="grow bg-gray-800	rounded-lg ml-2 mt-5">
      
          <div class="rounded-lg h-16 bg-gray-700 ml-5 flex" style="max-width:1230px">
            <div class="h-8 place-self-center flex">
                <div class="flex">
                    <div class="mt-1.5">
                        <span class="ml-4 text-white">구문</span>
                    </div>
                    <div class="mt-1.5 ml-4">
                        <select>
                            <option>전체</option>
                            <option>기타1</option>
                            <option>기타2</option>
                            <option>기타3</option>
                        </select>
                    </div>                    
              
                    <div class="flex">
                        <div class="ml-4" style="height: 30px;">
                            <input style="height: 25px;" class="mt-1.5 placeholder:italic placeholder:text-slate-400 block bg-white border border-slate-300 rounded-md pl-3 pr-3 shadow-sm focus:outline-none focus:border-sky-500 focus:ring-sky-500 focus:ring-1 sm:text-sm" placeholder="Search for anything..." type="text" name="search"/>
                        </div>
                        <div class="mt-1.5">
                            <p class="ml-4 text-white">사용</p>
                        </div>
                        <div class="mt-1.5 ml-4">
                            <select>
                                <option>전체</option>
                                <option>기타1</option>
                                <option>기타2</option>
                                <option>기타3</option>
                            </select>
                        </div>
                    </div>
                </div>
                

            </div>
            <div class="grow"></div>
              <button class="w-10 h-8 place-self-center mr-5 text-white rounded-lg bg-emerald-700 hover:bg-emerald-600 active:bg-emerald-500 focus:outline-none ">검색</button>
          </div>
          <!--상단 버튼 부분 -->
          <div class="flex pt-3 up-btn">
                <div class="grow"></div>
                <button @click="addBtn()" class="mr-4 bg-teal-500 rounded-lg hover:bg-teal-600 active:bg-teal-700 focus:outline-none" style="width: 100px"><p class="mt-0.5 ml-0.5 text-white">추가</p></button>               
          </div>
          <!-- 컨텐츠 부분 -->
          <div class="ml-5 mt-10 text-gray-200 project-table" style="width: 1294px; height: 600px;">
            <!-- 표 만들기 -->
            <!-- 상단 메뉴바 -->
            <div class=" bg-gray-700 h-10 mr-16 rounded-tl-lg rounded-tr-lg table-size">
              <div class="flex table-size">
                <div class="mr-3 pt-2 ml-5" style="width:150px"><p>프로젝트 코드</p></div>
                <div class="pt-2 border-l" style="width:480px"><p>프로젝트 명</p></div>
                <div class="pt-2 interval border-l"  style="width:150px"><p class="pr-2">PJ 시작일</p></div>
                <div class="pt-2 interval border-l"  style="width:150px"><p class="pr-2">PJ 종료일</p></div>
                <div class="pt-2 name-interval border-l"  style="width:150px"><p class="pr-2">PM</p></div>
                <div class="pt-2 interval border-l" style="width:120px;"><p class="pr-2">삭제</p></div>
              </div>
            </div>
           
            <!-- 실제 데이터 인풋 -->
            <div style="width:1294px; overflow: hidden">
              <div class=" bg-gray-600 mr-16 border-t" v-for="(project,index) in proj" v-bind:key="project.projectId">
                <div class="flex table-size">
                  <div class="mr-3 pt-2 ml-5 " style="width:150px"><p>{{ project.projectCode }}</p></div>
                  <div class="pt-2 textover border-l" style="height:40px; width: 480px;">
                    <p>{{ project.projectNm }} </p>
                  </div>
                  <div class="pt-2 interval border-l" style="width:150px"><p>{{ startedAt[index] }}</p></div>
                  <div class="pt-2 interval border-l" style="width:150px"><p >{{ endedAt[index] }}</p></div>
                  <div class="pt-2 name-interval border-l" style="width:150px"><p class="pr-2">{{ project.projectManager }}</p></div>
                  <!--영업 사원 이름 ajax 통신에는 교체 요망-->
                  <!--SAP에 등록 날짜 임으로 ajax 요청 후에 교체 요망-->
                  <!---->
                  <div class="interval  bg-gray-600  border-l" style="width:120px;">
                    <button v-if="IsMyProject(project.isMyProject)" @click="DeleteData(project.projectId)" class="w-10 h-6 mt-2 text-gray-200 rounded-lg bg-red-500 hover:bg-red-600 active:bg-red-700 focus:outline-none ">삭제</button>
                  </div>
                </div>
              </div>
            </div>
            
          </div>

          
      </div>

</template>
<script>
import standardcode from '../assets/standardCode.json';
import axios from "axios";
import AdminProjectEdit from './AdminProjectEdit.vue';
export default {
  name: 'AdminProject',
  data(){
    return{
      //axios로 인해서 받은 데이터
      getProj : [],
      responseCode: 0,
      backMessage: '',

      proj : [], // AXIOS 교체 데이터 -------------------------------------------------------->
      startedAt : [],
      endedAt: [],
      modal: false,
    }
  },
  async mounted() {
    await this.GetProject()
    this.writeDate(this.proj);
  },
  methods:{
    //axios get 요청
    async GetProject(){
      await axios.get('/api/projects',{withCredentials:true})
          .then((res)=>{
            this.proj = res.data.result;
            this.responseCode = res.data.code;
            this.backMessage = res.data.message;
            console.log(this.proj)
            if(this.responseCode != 1000){
              alert(this.backMessage);
              this.logout();
            }
          })
          .catch((res)=>{
            console.error(res);
          })
    },
    async DeleteData(projectId){
      if(confirm('삭제하시겠습니까?') == true){
        await axios.post('/api/projects/admin/delete/'+projectId,{
          memberId: localStorage.getItem('memberId')
        },{withCredentials:true})
            .then((res)=>{
              if(res.data.code === 1000){
                alert('삭제 되었습니다.')
              }else{
                alert(res.data.message);
              }
            })
            .catch((res)=>{
              console.error(res);
            })
        await this.GetProject();
      }else{
        return false;
      }
    },
    writeDate(){
     for(var i = 0; i<this.proj.length;i++){
      this.startedAt[i] = [this.proj[i].startedAt.slice(0,4),'-',this.proj[i].startedAt.slice(4,6),'-',this.proj[i].startedAt.slice(6,8)].join('')
      this.endedAt[i] = [this.proj[i].endedAt.slice(0,4),'-',this.proj[i].endedAt.slice(4,6),'-',this.proj[i].endedAt.slice(6,8)].join('');
     }
   },
   IsMyProject(project){
    if(project === '1'){
      return false;
    }else{
      return true;
    }
    },
    IsMyProjectForDelete(project){
    if(project === '1'){
      return true;
    }else{
      return false;
    }
   },
    logout(){
      localStorage.setItem('memberId', '0')
      localStorage.setItem('memberNm','No');
      localStorage.setItem('grade','GEUST');
      localStorage.setItem('deptNm', '무소속');
      this.$router.push('/')
    },
    addBtn() {
      this.modal = !(this.modal)
    }
  },
  components: {
    AdminProjectEdit:AdminProjectEdit,
}
}
</script>

<style>
#app {
  width: 100vw;
  height: 100vh;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.up-btn{
    width: 1260px;
    margin-top: 20px;
}

.long-table{
    width: 400px;
}
.middle-table{
    width: 120px;
}
.short-table{
    width: 80px;
}
.table-size{
  height: 40px;
  width: 1230px;
}
.textover{
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>