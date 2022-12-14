<!--
오류 처리 -> taskhour가 0시간일 때 막기

-->
<template>
  <div class="relative">
    <div class="modal bg-gray-700 rounded-lg absolute">
      <div class="modal-title bg-emerald-500 rounded-t-lg text-gray-200 flex">
        <div><p class="ml-3 mt-3">계획</p></div>
        <div class="grow"></div>
        <!--실제 모달창 닫는 버튼-->
        <button style="width: 30px; height: 30px;" @click="closeAlertFunc" class="hover:bg-rose-500 active:bg-rose-600 focus:outline-none rounded mr-5 mt-3"><font-awesome-icon icon="fa-solid fa-xmark" /></button>
      </div>

      <!--상단 근로 시간 표시 부분-->
      <div class="modal-total-time bg-gray-600 mt-3 pt-3 rounded-md" style="margin-top:10px">
      <div class="flex text-gray-200 bg 	">
        <div class="ml-3 mr-3"><p>{{ planDay }}</p></div>
        <div v-if="workFromHome()" class="bg-sky-300 w-16 h-6 rounded-md">재택근무</div>
        <div class="grow"></div>
        <!--시작 시간-->
        <div class="text-black ml-3 mr-3">
          <select @change="onChangeStartHour_v2($event)" v-model="StartWorkTime">
            <option v-for="start in startTime" :value="start.value">{{ start.text }}</option>
          </select>
        </div>
        <div>~</div>
        <!--끝나는 시간-->
        <div class="text-black ml-3 mr-3">
          <p class="text-base text-gray-200">{{ EndWorkTime }}</p>
        </div>
        <div class="mr-4"><p>총 <span class="text-rose-400">{{ totalDayWorkTime }}</span>시간</p></div><!--데이터 실시간 변경-->
      </div>
      <div class="flex mt-3 ml-3 text-gray-200">
        <div class="text-sm mt-1"><p><span class="text-rose-400">12:00 ~ 13:00</span> 시간은 <span class="text-sky-400">점심시간(휴게시간)</span>으로 계산 됩니다. 
          <span class="text-rose-400">단, 휴일 및 휴일은 4시간당, 30분씩 휴게시간이 제외되어 다빈치에 반영됩니다.</span></p></div>
          <div class="grow"></div>
          <div class="mr-4">휴게시간 : <span class="text-sky-400">-1</span> 시간</div>
      </div>
     </div>


     <div class="flex">
      <div class="grow"></div>
        <div class="text-gray-200 flex mr-8 mt-4 text-xs">
                <div class="flex h-6">
                    <div class="mr-2 w-8 bg-emerald-500 rounded-xl"><p class="mt-1">확정</p></div>
                    <div><p class="mt-1">확정</p></div>
                </div>
                <div class="flex ml-3 h-6">
                    <div class="mr-2 w-8 bg-sky-500 rounded-xl"><p class="mt-1">PM</p></div>
                    <div><p class="mt-1">PM승인</p></div>
                </div>
                <div class="flex ml-3 h-6">
                    <div class="mr-2 w-8 bg-fuchsia-500 rounded-xl"><p class="mt-1">팀장</p></div>
                    <div><p class="mt-1">팀장승인</p></div>
                </div>
                <div class="flex ml-3 h-6">
                    <div class="mr-2 w-10 bg-indigo-500 rounded-xl"><p class="mt-1">사업부</p></div>
                    <div><p class="mt-1">사업부승인</p></div>
                </div>
                <div class="flex ml-3 h-6">
                    <div class="mr-2 w-8 bg-rose-500 rounded-xl"><p class="mt-1">마감</p></div>
                    <div><p class="mt-1">마감</p></div>
                </div>
            </div>
        </div>
          <!-- 프로젝트 작성 란-->
          <div class="text-gray-200">
            <div class="mt-4 plan-box rounded">
              <div class="flex mb-2">
                <div class="ml-2 border-l border-emerald-400 ml-3"><p class="ml-3">계획업무</p></div>
                <div class="grow"></div>
                <button v-if="deleteBox" @click="removeTaskBox" class="mr-2 bg-rose-500 w-6 rounded-md hover:bg-rose-600 active:bg-rose-700 focus:outline-none"><font-awesome-icon icon="fa-solid fa-xmark" /></button>
                <button @click="addTaskBox" class="pt-0.5 rounded-md bg-sky-500 w-6 h-6 mr-2 hover:bg-sky-600 active:bg-sky-700 focus:outline-none"><font-awesome-icon icon="fa-solid fa-plus"/></button>
              </div>
              <!-- 데이터 반복 구간-->
              <div v-for="(task,index) in sendTaskData" class="border-2 modal-content rounded"   style="width: 99%; margin-bottom:10px">
                <div class="flex">

                  <div class="mt-3 ml-3 select-option rounded" style="width: 300px;">
                    <select v-model="task.groupSubId" @change="onChangeSelectMain($event, index)" class="rounded text-black" style="width: 300px">
                        <option v-for="main in mainGroup" :value="main.groupSubId">{{ main.codeMainNm }}</option>
                    </select>
                  </div>

                  <div class="mt-3 ml-3 select-option" style="width: 400px">
                    <select v-model="task.codeId" @change="onChangeSelectSub($event, index)" class="rounded" style="width: 400px">
                      <option v-for="subtask in subTaskCopy[index]" :value="subtask.codeId">{{ subtask.codeSubNm }}</option>
                    </select>
                  </div>

                  <div class="grow"></div>

                </div>
                <div class="flex">
                  <div><div class="ml-3 mt-2">
                    <input v-model="task.workDetail" style="width:300px; height: 30px;" class="mt-4 placeholder:italic placeholder:text-slate-400 block bg-white w-full border border-slate-300 rounded-md pl-3 pr-3 shadow-sm focus:outline-none focus:border-sky-500 focus:ring-sky-500 focus:ring-1 sm:text-sm" placeholder="기타사항을 입력하세요" type="text" name="search"/> 
                  </div></div>
                  <div class="grow"></div>
                  <div  style="height:25px; width: 110px;" class="mt-3 mr-3 w-16 rounded flex">
                    <p class="pl-1">시간 :</p>
                    <div>
                      <input v-model="task.taskHour" @change="onChangeTaskHour_v2($event, index) " type="number"  min="1" class="ml-3 form-control text-sm h-6 block w-12 pl-2 text-base font-normal text-gray-700 bg-white text-black bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none" />
                    </div>
                  </div>
                  <div style="height:25px;" class="mt-3 mr-3 bg-gray-700 w-28 rounded">{{renderTaskStartHour[index]}} ~
                    {{ renderTaskEndHour[index] }}</div><!--시간 데이터 실시간 변경점-->
                </div>
              </div>
                <!-- 업무 반복 구현 끝-->
               
                
               
            </div>
            <div>

          </div>
        </div>
        <div class="flex text-gray-200 mt-10 pb-10">
          <div class="grow"></div>
          <button @click="PostData('0')" class="bg-gray-500 mr-4 w-10 hover:bg-gray-600 active:bg-gray-700 focus:outline-none rounded">저장</button>
          <button @click="PostData('1')" class="bg-emerald-500 w-10 hover:bg-emerald-600 active:bg-emerald-700 focus:outline-none rounded">확정</button>
          <div class="grow"></div>
        </div>  

    </div>
  </div>


</template>
<script>
import taskType from '../assets/task.json';
import project from '../assets/project.json';
import { library } from '@fortawesome/fontawesome-svg-core'

/* add some free styles */
import {faXmark} from '@fortawesome/free-solid-svg-icons'
import {faPlus} from '@fortawesome/free-solid-svg-icons'
import axios from 'axios';

/* add each imported icon to the library */
library.add(faXmark, faPlus)

export default {
  name: 'PlanDetailEdit',
  props:{
    oneDayInfo: Object,
    DayWorkTime: String,
  },
  async mounted() {
    await this.getTask();
    await this.getMyProject();

    this.SetData()
    this.SetTaskHour_v2()
    this.ClassifyTaskType()
    this.ClassifyProject()
    this.MountSelect()
  },
  data(){
    return{
      //받는 데이터
      planDay:'',
      taskType: [],
      project: [],
      //변경 데이터에 대한 오류 여부

      //보내는 데이터
      DeleteData: [],
      sendTaskData:[],
      sendDeleteData:{
        planIds: [],
      },
      //보여주는 데이터
      default_value : 'selected',
      startTime:[
        {text: "8:00", value:"0800"},
        { text: "8:30", value: "0830"},
        {text: "9:00", value: "0900"},
        {text: "9:30", value: "0930"},
        {text: "10:00", value: "1000"}
      ],
      endTime:[
        {text: "16:00", value:"1600"},
        {text: "16:30", value: "1630"},
        {text: "17:00", value: "1700"},
        {text: "17:30", value: "0930"},
        {text: "18:00", value: "1800"},
        {text: "18:30", value: "1830"},
        {text: "19:00", value: "1900"},
        {text: "19:30", value: "1930"},
        {text: "20:00", value: "2000"}
      ],
      //끝나는 시간 데이터
      StartWorkTime: "",
      EndWorkTime: "",



      // 대분류, 소 분류 -> 따로 데이터를 받아오는 값
      mainGroup:[],
      subTask:[],
      subTaskCopy:[],
      subProejct:[],

      //task를 생성했을 때 기본으로 제공해주는 값
      defaultStartTime: "0900", //this.sendTaskDayInfo[index].groupMainId
      defalutEndTimeData: "1800", //this.sendTaskDayInfo[index].groupMainId

      defalutMainGroupData: "TR001", //this.sendTaskDayInfo[index].groupMainId
      defaultSubGroupData: "ZDUM1", //this.sendTaskDayInfo[index].groupMainId
      defaultCodeData : "Z001", //this.sendTaskDayInfo[index].groupMainId
      defaultGroupName: "주업무",
      defaultCodeName:"R&D 및 내부 PJT (NonPJT코드) - 시장조사, 분석, 계획, 설계/개발/테스트/이행",
      defaultWorkDetail: this.defaultGroupName,
      defaultwfhYn: "0",
      defaultenrollYn:"0",
      defaultmemberId: "",
      defaultisHoliday:"N",



      //데이터 상자
      TaskBox: [],
      deleteBox: false,


      // =============== 시간에 대한 알고리즘 ================
      planStartHour : "",
      taskStartHour : [],
      taskEndHour : [],
      taskTime :[],
      renderTaskStartHour:[],
      renderTaskEndHour:[],
      totalDayWorkTime: 0,
    }
  },
  computed:{
  },
  methods:{
    //혹시라도 url상으로 접근이 가능하기 때문에, 이를 프론트 상의 코드로 막아줘야한다.(axios가 필요하지 않는 모든 페이지에서 처리 해야함)
    /*-------------------------------------------- axios get 통신 ------------------------------------------- */
     async getTask(){
      var testtask
      await axios.get('/api/tasks',{withCredentials:true})
      .then((res)=>{
        if(res.data.code === 1000){
          this.taskType = res.data.result;
        }else{
          alert(this.backMessage);
          this.logout();
        }
      })
      .catch((res)=>{
        console.error(res)
      })
    },
    async getMyProject(){
      await axios.get('/api/projects/me',{withCredentials:true})
      .then((res)=>{
        if(res.data.code === 1000){
          this.project = res.data.result;
        }else{
           alert(this.backMessage);
          this.logout();
        }
      })
      .catch((res)=>{
        console.error(res);
      })
    },
    /*------------------------------------------ 마운트 시에 일어나야할 이벤트 -----------------------------------*/
    SetData(){
      //각 Task가 순서대로 올 때를 가정하고 함수를 진행한다.
      if(this.oneDayInfo.length > 1){
        this.deleteBox = true;//삭제 버튼 활성화
      }

      for(var i = 0;i<this.oneDayInfo.length;i++){
        console.log(this.deleteBox)
        this.sendTaskData.push({
          planId: this.oneDayInfo[i].planId,
          seq: this.oneDayInfo[i].seq,
          taskHour: this.oneDayInfo[i].taskHour,
          planDay: this.oneDayInfo[i].planDay,
          dayHour: this.oneDayInfo[i].dayHour,
          startedHour: this.oneDayInfo[i].startedHour,
          endedHour: this.oneDayInfo[i].endedHour,
          groupMainId: this.oneDayInfo[i].groupMainId,
          groupSubId: this.oneDayInfo[i].groupSubId,
          codeId: this.oneDayInfo[i].codeId,
          codeMainNm: this.oneDayInfo[i].codeMainNm,
          codeSubNm: this.oneDayInfo[i].codeSubNm,
          workDetail: this.oneDayInfo[i].workDetail,
          wfhYn: this.oneDayInfo[i].wfhYn,
          enrollYn: this.oneDayInfo[i].enrollYn,
          memberId: this.oneDayInfo[i].memberId,
          isHoliday: this.oneDayInfo[i].isHoliday
        });
      }
      var planDay = String(this.oneDayInfo[0].planDay)
      this.planDay = [planDay.slice(0,4),'년 ',planDay.slice(4,6),'월 ',planDay.slice(6,8),'일'].join('')

      console.log(this.sendTaskData)
    },
    MountSelect() { // subTask 완성
      //MainTask - 완성
      //이거 혹시 sendTaskData가 여러개라면, 여러개를 뿌려줘야하는데
      for(var j = 0;j<this.oneDayInfo.length;j++){
        this.subTaskCopy[j] = [...this.subTask];
      }


      for(var i = 0;i<this.oneDayInfo.length; i++){
        for(var j = 0; j<this.subTaskCopy[i].length; j++){

          if(this.subTaskCopy[i][j].groupSubId != this.oneDayInfo[i].groupSubId){
            this.subTaskCopy[i].splice(j,1)
            j--;
          }
        }
      }
    },
    ClassifyTaskType(){//완성
      for(var i = 0;i<this.taskType.length;i++){
        if(this.taskType[i].groupId === 'TR001'){
          this.mainGroup.push({groupMainId: this.taskType[i].groupId, groupSubId: this.taskType[i].codeId, codeMainNm: this.taskType[i].codeNm});
        }else{
          this.subTask.push({groupSubId: this.taskType[i].groupId, codeId: this.taskType[i].codeId, codeSubNm: this.taskType[i].codeNm});
        }
      }
    },
    ClassifyProject(){
      for(var i = 0;i<this.project.length;i++){
        this.mainGroup.push({groupMainId: this.project[i].projectCode, groupSubId: 'TR002',codeMainNm: this.project[i].projectNm});
      }
    },


    /*---------------------------------------- change 이벤트 메서드 ---------------------------------*/

    //
    // 시간 변경 시에 실시간으로 시간 변경 메서드
    //
    //실적 업무 박스 추가시에 추가 되는 메서드
    addTaskBox(){
      var index = this.sendTaskData.length;
      this.sendTaskData.push({
        perfId: "",
        seq: index+1,//박스의 길이대로 순서 작성
        taskHour: 1,
        planDay: this.oneDayInfo[0].planDay,
        dayHour: 0,
        startedHour: this.sendTaskData[index-1].endedHour,
        endedHour: this.sendTaskData[index-1].endedHour,
        groupMainId: this.defalutMainGroupData,
        groupSubId: this.defaultSubGroupData,
        codeId: this.defaultCodeData,
        codeMainNm: this.defaultGroupName,
        codeSubNm: this.defaultCodeName,
        workDetail: "",
        wfhYn: this.defaultwfhYn,
        enrollYn: this.defaultenrollYn,
        memberId: this.defaultmemberId,
        isHoliday: this.defaultisHoliday
      });

      this.taskStartHour.push(Number(this.sendTaskData[index].startedHour));
      this.taskEndHour.push(Number(this.sendTaskData[index].endedHour));
      this.deleteBox = true;
      this.addSelectMain(index)
    },
    removeTaskBox(){
      var delData = this.sendTaskData.pop()
     
      var data = delData.planId
      this.DeleteData.push(data)

      let arr = Object.values(this.DeleteData);

      console.log(arr);
      this.sendDeleteData.planIds = [...arr];
      this.taskStartHour.pop();
      this.taskEndHour.pop();
      this.RenderTime_v2()
      
      if(this.sendTaskData.length<2){
        this.deleteBox = false
      }

      //삭제시에 보내는 데이터 senddata 삭제
    },
    addSelectMain(index){
      var sub = this.sendTaskData[index].groupSubId

      for(var i = 0;i<this.mainGroup.length;i++){
        if(sub === this.mainGroup[i].groupSubId){
          this.sendTaskData[index].groupMainId = this.mainGroup[i].groupMainId;
          this.sendTaskData[index].codeMainNm = this.mainGroup[i].codeMainNm;
          break;
        }
      }
      this.subTaskCopy[index] = [...this.subTask];


      for(var i = 0; i < this.subTaskCopy[index].length; i++){
        if(sub != this.subTaskCopy[index][i].groupSubId){
          this.subTaskCopy[index].splice(i,1);
          i--;
        }
      }
    },

    onChangeSelectMain(event, index){
      var sub = event.target.value

      this.sendTaskData[index].groupSubId = sub;

      for(var i = 0;i<this.mainGroup.length;i++){
        if(sub === this.mainGroup[i].groupSubId){
          this.sendTaskData[index].groupMainId = this.mainGroup[i].groupMainId;
          this.sendTaskData[index].codeMainNm = this.mainGroup[i].codeMainNm;
          break;
        }
      }
      this.subTaskCopy[index] = [...this.subTask];


      for(var i = 0; i < this.subTaskCopy[index].length; i++){
        if(sub != this.subTaskCopy[index][i].groupSubId){
          this.subTaskCopy[index].splice(i,1);
          i--;
        }
      }
    },
    onChangeSelectSub(event, index){

      var code = event.target.value;

      this.sendTaskData[index].codeId = code;

      for(var i = 0;i<this.subTask.length;i++){
        if(this.subTask[i].codeId === code){
          this.sendTaskData[index].codeSubNm = this.subTask[i].codeSubNm;
        }
      }

    },
    // 데이터 보내는 메서드
    saveData(status){
      for(var i = 0; i<this.sendTaskData.length;i++){
        this.sendTaskData[i].enrollYn = status;
      }

    },
    workFromHome(){
      if(this.oneDayInfo[0].wfhYn === '0'){
        return false;
      }else{
        return true;
      }
    },
    /*---------------------------------------- 경고 창 띄우는 메스드 -------------------------------------*/
    async saveAlertFunc(){
      if(confirm('저장하시겠습니까?') == true){
        
      }else{
        return false;
      }
    },
    //alert 창 모음
    closeAlertFunc(){
      if(confirm('지금 닫으시면 저장이 되지 않습니다.') == true){
        this.$emit('closeEditModal');
      }else{
        return false;
      }
      // alert('지금 닫으시면 저장이 되지 않습니다.');
    },
    //==================================== 시간 계산에 대한 알고리즘 2 ======================================
    //처음 렌더링 될 때 사용되는 메서드
    SetTime_v2(){
    },
    SetTaskHour_v2(){
      for(var i = 0;i<this.sendTaskData.length;i++){
        this.taskStartHour.push(Number(this.sendTaskData[i].startedHour))
        this.taskEndHour.push(Number(this.sendTaskData[i].endedHour))
        this.taskTime.push(this.sendTaskData[i].taskHour)
      }
      this.totalDayWorkTime = this.sendTaskData[0].dayHour;
      this.RenderTime_v2()

    },
    onChangeStartHour_v2(e){
      var daystart = e.target.value;
      daystart = Number(daystart);
      this.taskStartHour[0] = daystart;

      this.calTime_v2();
    },
    onChangeTaskHour_v2(e,index){

      this.taskTime[index] = Number(e.target.value);
      var sumTime = 0
      for(var i = 0;i<this.taskTime.length;i++){
         sumTime += this.taskTime[i]
      }
      this.totalDayWorkTime = sumTime

      this.calTime_v2();

    },//시간 계산을 해주는 메서드 -> 시간 계산이 필요할 때 활용하면 된다.
    calTime_v2(){
      //12시 이전에 계획이 끝나는 경우
      var time = 0;
      //12시 이후에 계획이 끝나는 경우
      var lunch = 0;
      var lunchTime = 100;
      for(var i = 0;i<this.taskTime.length;i++){
        time = this.taskStartHour[i]+(this.taskTime[i]*100);
        if(lunch == 0 && time > 1200){
          time += lunchTime;
          this.taskEndHour[i] = time
          lunch++;
        }else if(time === 1200 && lunch === 0){
          this.taskEndHour[i] = time;
          time += lunchTime;
          lunch++;
        }else{//12시 이전이거나 혹은 12시 이후 일때
          this.taskEndHour[i] = time;
        }
        if(i < this.taskTime.length-1){
          this.taskStartHour[i+1] = time;
        }

      }

      this.RenderTime_v2();
    },
    RenderTime_v2(){
      for(var i = 0;i<this.taskStartHour.length;i++){
        var start = this.taskStartHour[i]
        var end = this.taskEndHour[i]

        if(start/100 < 10){
          start = '0'+String(start);
        }else {
          start = String(start);
        }
        if(end/100 < 10){
          end = '0'+String(end)
        }else{
          end = String(end);
        }
        this.renderTaskStartHour[i] = [start.slice(0,2),':',start.slice(2,4)].join('');
        this.renderTaskEndHour[i] = [end.slice(0,2),':',end.slice(2,4)].join('');
      }

      var start =  this.taskStartHour[0]
      this.StartWorkTime = '0'+String(start)
      
      var index = this.taskStartHour.length-1
      var EndTime = this.taskEndHour[index]

      if(EndTime > 2400){
        EndTime -= 2400;
        this.taskEndHour[index] = EndTime;
        if(EndTime === 30){
          var EndDay = '00'+String(EndTime);
        }else{
          var EndDay = '0'+String(EndTime);
        }
      }else{
        var EndDay = String(EndTime);
      }
      
      this.EndWorkTime = [EndDay.slice(0,2),':',EndDay.slice(2,4)].join('');

    },
    //================================ 데이터 보내기 ===================================
    // ------------------------------------------------------AXIOS POST 통신 ------------------------------------------------------ 

    async PostData(status){
      for(var i = 0;i<this.sendTaskData.length;i++){
        var stringTaskStartHour = this.taskStartHour[i]
        var stringTaskEndHour = this.taskEndHour[i]
        if(stringTaskStartHour/100 < 10){
          stringTaskStartHour = '0'+String(stringTaskStartHour);
        }else {
          stringTaskStartHour = String(stringTaskStartHour);
        }
        if(stringTaskEndHour/100 < 10){
          stringTaskEndHour = '0'+String(stringTaskEndHour)
        }else{
          stringTaskEndHour = String(stringTaskEndHour);
        }

        this.sendTaskData[i].taskHour =  this.taskTime[i];
        this.sendTaskData[i].dayHour = this.totalDayWorkTime;
        this.sendTaskData[i].startedHour = stringTaskStartHour;
        this.sendTaskData[i].endedHour = stringTaskEndHour;
        this.sendTaskData[i].enrollYn = status;
      }
      console.log(this.sendTaskData)


      await axios.patch('api/plans',this.sendTaskData,{withCredentials : true})
      .then((res)=>{
          if(res.data.code === 1000 && status === '0'){
            alert('수정된 데이터가 저장 처리 되었습니다.')
          }else if(res.data.code === 1000 && status === '1'){
            alert('수정된 데이터가 확정 처리 되었습니다.')
          }else{
            alert(res.data.message);
            this.$router.go(-1)
          }//로그인이 아닌 경우는 이곳에서 else if로 튕겨주기
      })
      .catch((res)=>{
        console.error(res)
      })

      await axios.post('api/plans/delete',this.sendDeleteData,{
        params:{
          day: String(this.sendTaskData[0].perfDay)
        },withCredentials:true})
        .then((res)=>{
          if(res.data.code != 1000){
            alert(res.data.message)
          }
        })
        .catch((res)=>{
          console.error(res)
        })
    },
    logout(){
      localStorage.setItem('memberId', '0')
      localStorage.setItem('memberNm','No');
      localStorage.setItem('grade','GEUST');
      localStorage.setItem('deptNm', '무소속');
      this.$router.push('/')
    },
    // onChangeTaskTime(event,index){
      //   var taskTime = event.target.value;
      //   if(taskTime< 0){
      //     alert("양수만 입력해주시길 바랍니다.")
      //     this.sendTaskData[index].taskHour = 0
      //   }else{
      //     // 시간 데이터가 들어올 때 task 단위로 시간을 계산하는 로직
      //     // task수행 시간을 받아오게 되면,
      //     this.StartWorkTime = this.oneDayInfo[0].startedHour;// 시작 시간에 대한 데이터
      //     this.TotalWorkTime = this.oneDayInfo[0].dayHour;//끝나는 시간에 대한 데이터( 일한 시간을 바탕으로 따로 계산)
      //
      //
      //     var start = Number(this.StartWorkTime); // 시작 시간에 대한 데이터를 Number화 해서 계산을 진행함
      //     var end = String(start + (this.TotalWorkTime*100)+100);
      //
      //
      //
      //     var taskstart = start, lunch=0;
      //     this.TaskStartTime[0] = taskstart;
      //
      //     this.sendTaskData[index].taskHour = taskTime;
      //
      //     for(var i = 0;i<this.sendTaskData.length;i++){
      //       var taskhour = (this.sendTaskData[i].taskHour*100)
      //
      //       taskstart += taskhour;
      //       if(taskstart >= 1700 && lunch == 0){
      //         taskstart += 100;
      //         this.TaskEndTime[i] = taskstart;
      //         break;
      //       }//특수한 경우는 따로 뺌,, -> 한 task의 8시간 일때
      //
      //       if(taskstart < end){ //task더한 값이 end 보다 작을 때만 돌기
      //         if(taskstart == 1200 || (taskstart > 1200 && lunch === 0) || (taskstart+=100) <= end){ //
      //           taskstart += 100;
      //           this.TaskEndTime[i] = taskstart; // 끝나는 시간 넣기
      //           this.TaskStartTime[i+1] = taskstart;
      //           lunch++;
      //         }
      //       }else{
      //         this.TaskEndTime.push(end);
      //       }
      //     }
      //     // 시간을 출력할 때 사용하는 로직
      //     for(var i = 0; i<this.TaskStartTime.length;i++){
      //       var startview, endview
      //       if(this.TaskStartTime[i]/100 < 10){
      //         startview = '0'+String(this.TaskStartTime[i]);
      //         this.sendTaskData[i].startedHour = startview;
      //       }else{
      //         startview = String(this.TaskStartTime[i]);
      //         this.sendTaskData[i].startedHour = startview;
      //       }
      //       if(this.TaskEndTime[i]/100 < 10){
      //         endview = '0'+ String(this.TaskEndTime[i]);
      //         this.sendTaskData[i].endedHour = endview
      //       }else{
      //         endview = String(this.TaskEndTime[i]);
      //         this.sendTaskData[i].endedHour = endview
      //       }
      //       this.TaskStartTimeView[i] = [startview.slice(0,2),':',startview.slice(2,4)].join('');
      //       this.TaskEndTimeView[i] = [endview.slice(0,2),':',endview.slice(2,4)].join('');
      //     }
      //
      //   }
      //
      // },
    // CalTime(){
    //   this.StartWorkTime = this.oneDayInfo[0].startedHour;
    //   this.TotalWorkTime = this.oneDayInfo[0].dayHour;
    //
    //   var start = Number(this.StartWorkTime);
    //   var end = String(start + (this.TotalWorkTime*100)+100);
    //   this.EndWorkTime = [end.slice(0,2),':',end.slice(2,4)].join('');
    //   // 시간 데이터가 들어올 때 task 단위로 시간을 계산하는 로직
    //   var taskstart = start, lunch=0;
    //   this.TaskStartTime.push(taskstart);
    //
    //   for(var i = 0;i<this.oneDayInfo.length;i++){
    //     var taskhour = (this.oneDayInfo[i].taskHour*100)
    //
    //     taskstart += taskhour;
    //     if(taskstart == 1700){
    //       taskstart += 100;
    //       this.TaskEndTime.push(taskstart);
    //       break;
    //     }//특수한 경우는 따로 뺌,, -> 한 task의 8시간 일때
    //
    //     if(taskstart < end){ //task더한 값이 end 보다 작을 때만 돌기
    //       if(taskstart == 1200 || (taskstart > 1200 && lunch === 0) || (taskstart+=100) <= end){ //
    //         taskstart += 100;
    //         this.TaskEndTime.push(taskstart); // 끝나는 시간 넣기
    //         this.TaskStartTime.push(taskstart);
    //         lunch++;
    //       }
    //     }else{
    //       this.TaskEndTime.push(end);
    //     }
    //   }
    //   // 시간을 출력할 때 사용하는 로직
    //   for(var i = 0; i<this.TaskStartTime.length;i++){
    //     var startview, endview
    //     if(this.TaskStartTime[i]/100 < 10){
    //       startview = '0'+String(this.TaskStartTime[i]);
    //     }else{
    //       startview = String(this.TaskStartTime[i]);
    //     }
    //     if(this.TaskEndTime[i]/100 < 10){
    //       endview = '0'+ String(this.TaskEndTime[i]);
    //     }else{
    //       endview = String(this.TaskEndTime[i]);
    //     }
    //     this.TaskStartTimeView[i] = [startview.slice(0,2),':',startview.slice(2,4)].join('');
    //     this.TaskEndTimeView[i] = [endview.slice(0,2),':',endview.slice(2,4)].join('');
    //   }
    //
    // },
  },
}
</script>

<style>

.modal{
  width: 1200px;
  height: 450px;
  left: 300px;
  top: 100px;
  z-index: 1;
  overflow: scroll;
}
.modal-title{
  width: 1000px;
  height: 50px;
}
.modal-total-time{
  width: 1000px;
  height: 80px;
  margin: auto;
}
.modal-content{
  margin: auto;
  width: 1000px;
  height: 100px;
}
.select-option{
    color: black;
    width: 300px;
    overflow: hidden;
    text-overflow: ellipsis;
}
.plan-box{
  width: 1150px;
  margin: 10px 20px 0 23px;
  padding: 10px;
  background-color: rgb(75 85 99);
}

</style>
