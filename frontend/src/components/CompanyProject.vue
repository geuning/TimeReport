<!--
추가 사항
  1. 데이터 바인딩을 통해서 반복문 돌리기, ajax 사용하기
  2. 최대 height를 지정하고 overflow: scroll 적용하기(때에 따라서 무한 스크롤로 돌려보기)

개선 사항
  1. 위에 확정 표기 지우고 다시 재정비하기
  2. 추가 버튼 boarder 삭제하기
-->
<template>
      <!--검색 부분 -->
      <div class="grow bg-gray-800	rounded-lg ml-2 mt-5">
          <div class="rounded-lg h-16 bg-gray-700 ml-5 flex" style="max-width:1294px">
            <div class="h-8 place-self-center flex">
                <div class="flex">
                    <div class="mt-1.5">
                        <span class="ml-4 text-gray-200">주 시작일</span>
                    </div>
                    <div class="mt-1.5 ml-4">
                        <select>
                            <option>2021</option>
                            <option>2022</option>
                            <option>2023</option>
                            <option>2024</option>
                            <option>2025</option>
                            <option>2026</option>
                        </select>
                    </div>                    
                    
                </div>
                
                <div class="flex">
                    <div class="mt-1.5">
                        <p class="ml-4 text-gray-200" style="width:100px">성명/사번</p>
                    </div>
                    <div class="">
                        <input class="h-8 placeholder:italic placeholder:text-slate-400 block bg-white w-full border border-slate-300 rounded-md pl-3 pr-3 shadow-sm focus:outline-none focus:border-sky-500 focus:ring-sky-500 focus:ring-1 sm:text-sm" placeholder="Search for anything..." type="text" name="search"/> 
                    </div>
                </div>
                

            </div>
            <div class="grow"></div>
            
              <button class="w-10 h-8 place-self-center mr-5 text-gray-200 rounded-lg bg-green-500 hover:bg-green-600 active:bg-green-700 focus:outline-none ">검색</button>
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
                <div class="pt-2 interval border-l" style="width:120px;"><p class="pr-2">추가</p></div>
              </div>
            </div>
           
            <!-- 실제 데이터 인풋 -->
            <div style="width:1294px; overflow: hidden">
              <div class=" bg-gray-600 mr-16 border-t" v-for="(project,index) in proj" >
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
                    <button v-if="IsMyProject(project.isMyProject)" @click="PostData(project.projectId)" class="w-10 h-6 mt-2 text-gray-200 rounded-lg bg-sky-500 hover:bg-sky-600 active:bg-sky-700 focus:outline-none ">참여</button>
                    <button v-if="IsMyProjectForDelete(project.isMyProject)" @click="DeleteData(project.projectId)" class="w-10 h-6 mt-2 text-gray-200 rounded-lg bg-red-500 hover:bg-red-600 active:bg-red-700 focus:outline-none ">삭제</button>
                  </div>
                </div>
              </div>
            </div>
            
          </div>

          
      </div>

</template>
<script>
import project from '../assets/projData.json';
import axios from "axios";
export default {
  name: 'CompanyProject',
  data(){
    return{
      //axios로 인해서 받은 데이터
      getProj : [],
      responseCode: 0,
      backMessage: '',

      proj : [], // AXIOS 교체 데이터 -------------------------------------------------------->
      startedAt : [],
      endedAt : [],
    }
  },
  async mounted() {
    await this.GetProject()
    this.writeDate(project);
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
    async PostData(projectId){
      if(confirm('추가하시겠습니까?') == true){
        await axios.post('/api/projects/'+projectId,{
          memberId: localStorage.getItem('memberId')
        },{withCredentials:true})
            .then((res)=>{
              if(res.data.code === 1000){
                alert('추가 되었습니다.')
              }else{
                alert(res.data.message);
              }
            })
            .catch((res)=>{
              console.error(res);
            })
        await this.GetProject();
        this.writeDate();
      }else{
        return false;
      }
    },
    async DeleteData(projectId){
      if(confirm('삭제하시겠습니까?') == true){
        await axios.post('/api/projects/delete/'+projectId,{
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


  },
  components: {
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
.interval{
  width: 100px;
}
.name-interval{
  width: 100px;
}
.table-size{
  height: 40px;
}
.textover{
  overflow-x: hidden;
  text-overflow: ellipsis;
}
.project-table{
  overflow-y: scroll;
  overflow-x: hidden;
}
.project-table::-webkit-scrollbar{
  width: 5px;
  border-radius: 5px;
  }
.project-table::-webkit-scrollbar-thumb{
  border-radius: 5px;
  background-color: rgb(100 116 139);
}

</style>
