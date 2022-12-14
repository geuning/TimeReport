<!--

실적 캘린더

-->


<template class="relative">
  <div class="relative modal-position">
    <PerfDetailVue v-if="viewModal" @closeModal="showViewModal()" @showEditModal="showViewModalEdit(), showViewModal()" :oneDayInfo="sendDetailInfo" :DayWorkTime="sendDayWorkTime"/>
    <PerfDetailEditVue v-if="editModal" @closeEditModal="showViewModalEdit()" :oneDayInfo="sendDetailInfo"/>
  </div>
    <div>
      <div class="text-gray-200 flex ml-8 text-xl">
        <h1>실적관리</h1>
      </div>
      <div class="grow"></div>
         <!--검색 부분 -->
      <div class="bg-gray-800 rounded-lg ml-2 mt-5">
                
          <div class="rounded-lg h-16 bg-gray-600	 ml-5 flex" style="max-width:1230px">
            <div class="h-8 place-self-center flex">
              <div class="flex">
                <div class="mt-1.5">
                  <span class="ml-4 text-gray-200">년도</span>
                </div>
                <div class="mt-1.5 ml-4">
                  <select v-model="selectYear" @change="calSelectWeek()">
                    <option  v-for="year in searchWeekYear" :value="year">{{ year }}</option>
                  </select>
                </div>
                <div class="mt-1.5 ml-4">
                  <div>
                    <select v-model="selectWeek">
                      <option v-for="week in copySearchWeekly" :value="week.fromdt">{{ week.content }}</option>
                    </select>
                  </div>
                </div>
              </div>

            </div>
            <div class="grow"></div>
            <div class="text-gray-200 mr-3 mt-2" style="max-width:1200px">
                <div>
                  <p class="text-left	">1주차 계획: <span class="text-cyan-400">{{ FirstPlanTotalHour }}Hr</span> - 개인실적: <span class="text-emerald-400">{{FirstPerfTotalHour}}Hr</span></p>
                </div>
                <div>
                  <p class="text-left	">2주차 계획: <span class="text-cyan-400">{{ SecondPlanTotalHour }}Hr</span> - 개인실적: <span class="text-emerald-400">{{ SecondPerfTotalHour }}Hr</span></p>
                </div>
            </div>
            <div class="mr-5 mt-5 text-gray-200">
              <p>2주 총 실적 : <span class="text-rose-500">{{ FirstPerfTotalHour+SecondPerfTotalHour }}Hr</span></p>
            </div>
              <button @click="SearchDate()" class="w-10 h-8 place-self-center mr-5 text-gray-200 rounded-lg bg-emerald-700 hover:bg-emerald-600 active:bg-emerald-500 focus:outline-none ">검색</button>
          </div>
          <div class="flex text-gray-200" style="max-width:1265px">
            <div class="ml-7 mt-7 text-xl">
                <p>{{ departmentName }}</p>
            </div>
            <div class="grow"></div>

          </div>



        <!-- 컨텐츠 부분 -->
        <div class="ml-5 mt-10">
          <!--상단 요일 표시-->
          <div class="flex flex-row " style="width:1200px;">
            <div class="calendar-interval-top" v-for="(day, index) in DayOfTheWeek">
              <p class="bg-gray-600 text-gray-200 ">{{day}}</p>
              <!-- 1주차 월요일 -->
              <div class="relative calendar-interval bg-gray-200 box-border border-2 border-gray-300" style="width:175px;">
                <div class="flex">
                  <div class="ml-1 mt-1 flex-inital w-10 h-10 rounded" :class="[HolidayCheck[index] ? 'bg-rose-500':'bg-gray-500']" >
                    <p class="mt-2 text-gray-200">{{viewDate[index]}}</p>
                  </div>
                  <div v-if="showCalenderData(index)" class="grow mt-2">
                    <p >{{ DayWorkTime[index] }}</p>
                  </div>
                </div>

                <div v-if="showCalenderData(index)" class="mt-5 relative calendar-interval">
                  <div class="absolute ml-2" style="width: 400px;">
                    <p class="text-left text-ellipsis overflow-hidden" v-for="task in DailyTask[index]" >{{task}}</p>
                  </div>
                </div>
                <div v-if="confirm[index]" class="absolute bottom-3 ml-2 ">
                  <div  v-if="showCalenderData(index)" class="w-10 h-6 text-center text-gray-200 rounded-lg bg-emerald-500">확정</div>
                </div>
                <div v-if="approve[index]" class="absolute bottom-3 ml-2 ">
                  <div  v-if="showCalenderData(index)" class="w-10 h-6 text-center text-gray-200 rounded-lg bg-fuchsia-500">팀장</div>
                </div>
                <div v-if="ended[index]" class="absolute bottom-3 ml-2 ">
                  <div  v-if="showCalenderData(index)" class="w-10 h-6 text-center text-gray-200 rounded-lg bg-rose-500">마감</div>
                </div>
                <div class="absolute bottom-3 right-0 mr-2 ">
                  <button  v-if="showCalenderData(index)" @click="showViewModal(index)" class="w-10 h-6 text-center text-gray-200 rounded-lg bg-gray-500 hover:bg-gray-600 active:bg-gray-700 focus:outline-none ">보기</button>
                </div>
              </div>
              <!--2주차 월요일-->
              <div class="relative calendar-interval bg-gray-200 box-border border-2 border-gray-300" style="width:175px">
                <div class="flex">
                  <div class="ml-1 mt-1 flex-inital w-10 h-10 rounded" :class="[HolidayCheck[index+7] ? 'bg-rose-500':'bg-gray-500']">
                    <p class="mt-2 text-gray-200">{{viewDate[index+7]}}</p>
                  </div>
                  <div  v-if="showCalenderData(index+7)" class="grow mt-2">
                    <p>{{ DayWorkTime[index+7] }}</p>
                  </div>
                </div>

                <div class="mt-5 relative calendar-interval">
                  <div v-if="showCalenderData(index+7)" class="absolute ml-2" style="width: 400px;">
                    <p class="text-left text-ellipsis overflow-hidden" v-for="task in DailyTask[index+7]" >{{task}}</p>
                  </div>
                </div>
                  <div v-if="confirm[index+7]" class="absolute bottom-3 ml-2 ">
                    <div v-if="showCalenderData(index+7)" class="w-10 h-6 text-center text-gray-200 rounded-lg bg-emerald-500">확정</div>
                  </div>
                  <div v-if="approve[index+7]" class="absolute bottom-3 ml-2 ">
                    <div v-if="showCalenderData(index+7)" class="w-10 h-6 text-center text-gray-200 rounded-lg bg-fuchsia-500">팀장</div>
                  </div>
                  <div v-if="ended[index+7]" class="absolute bottom-3 ml-2 ">
                    <div v-if="showCalenderData(index+7)" class="w-10 h-6 text-center text-gray-200 rounded-lg bg-rose-500">마감</div>
                  </div>
                <div v-if="viewDetailBtn" class="absolute bottom-3 right-0 mr-2 ">
                  <button v-if="showCalenderData(index+7)" @click="showViewModal(index+7)" class="w-10 h-6 text-center text-gray-200 rounded-lg bg-gray-500 hover:bg-gray-600 active:bg-gray-700 focus:outline-none ">보기</button>
                </div>
              </div>
            </div>
            <!-- 끝 -->
          </div>
        </div>
      </div>
    </div>


</template>


<script>
import PerfDetailVue from './PerfDetail.vue'
import PerfDetailEditVue from './PerfDetailEdit.vue';
import plandata from '../assets/planData.json';
import perfdata from '../assets/perfData.json';
/*
  사용한 데이터 정리하기
  ----- 보낼 데이터 -----
  요일,
  휴일 여부,
  세부사항 페이지에 보낼 데이터,

  ------표시 데이터 셋-----
  총 시간,
  시간,
  일자,
  업무 표시 부분,

  seq단위로 데이터 뽑는걸 해봐봐야할듯?


*/
import axios from 'axios';
import weekly from "../assets/biweekly.json";
export default {
  name: 'PerfCalender',
  async mounted(){
    // this.calDate(this.perf[0].perfDay)
    // this.viewDay(this.Date)
    // this.calDayWorkTime(this.perf)


    await this.GetWeekData()
    this.calSelectYear()
    this.calSelectWeek()

    await this.GetServer();


    this.calDate_v2(this.Date)
    this.calDayOfData()
    this.calDayWorkTime_v2()
    this.printPrevWorkType(this.perf)
    this.DistinguishHoliday()
    this.calApprovalTime()
    this.DistinguishStatus()
    this.calTotalWeekTime()
    this.isDummyFunc()
  },
  data: function () {
    return {
      //상단에 날짜를 받아오는 변수
      getWeekly: [],
      searchWeekly: '', // AXIOS, twoWeekDtos 데이터 받는 부분 ------------------------------------------------>
      searchWeekYear: [],

      copySearchWeekly: [],

      selectWeek: '',// AXIOS, startOfWeek 데이터 받는 부분 ------------------------------------------------>
      selectYear: '',// AXIOS, startOfWeek.slice(0,4) 데이터 받는 부분 ------------------------------------------------>

      EndDayOfMonth: [31,28,31,30,31,30,31,31,30,31,30,31],

      selectDay : 0, //실제 파라미터 값으로 들어가는 값
      //axios로 인해서 받은 데이터
      Getperf : [],
      responseCode: 0,
      backMessage: '',
      //
      twoWeek: 14,
      //Detail button 유무
      viewDetailBtn: true,

      //Detail 창 띄워주는 변수
      viewModal: false,
      editModal: false,
      //Detail 창 데이터 전송
      sendDetailInfo: [],
      sendDayWorkTime: '',

      //받은 데이터
      perf: [], // ------------------------------------> 실제 get요청을 받는 곳
      plan: plandata,// ------------------------------------> 실제 get요청을 받는 곳

      //보낼 데이터
      DayOfTheWeek: ['Mon', 'Tue', 'Web', 'Thu','Fri','Sat','Sun'],
      Date: [],// 완료
      DayOfData: [],//완료
      planDayOfDate:[],
      HolidayCheck: [false,false,false,false,false,false,false,false,false,false,false,false,false,false],
      isDummy:[false,false,false,false,false,false,false,false,false,false,false,false,false,false],

      //표시 데이터 셋
      departmentName:'',
      viewDate:[], //완료
      DayWorkTime: [],
      DailyTask:[],
      approvalTime: 0,
      confirm : [false,false,false,false,false,false,false,false,false,false,false,false,false,false],
      approve : [false,false,false,false,false,false,false,false,false,false,false,false,false,false],
      ended : [false,false,false,false,false,false,false,false,false,false,false,false,false,false],

      FirstPlanTotalHour: 0,
      SecondPlanTotalHour: 0,
      FirstPerfTotalHour: 0,
      SecondPerfTotalHour : 0,

    }
  },
  methods:{
    //------------------------------------ AXIOS -------------------------------------------
    async GetServer(){
      
        await axios.get("/api/performances",{
          params:{
            day: this.selectWeek
          },withCredentials : true})
          .then((res)=>{
            console.log('보내주는 위크',this.selectWeek)
            console.log(res)
            this.perf = res.data.result;
            console.log(this.perf)
            this.responseCode = res.data.code;
            this.backMessage = res.data.message;
            if(this.responseCode != 1000){
              alert(this.backMessage);
              this.logout();
            }
          })
          .catch((response)=>{
            console.log(response);
          })
      
    },
    async GetWeekData(){
        await axios.get('/api/biweekly',{withCredentials: true})
            .then((res)=>{
              if(res.data.code === 1000){
                this.searchWeekly = res.data.result.twoWeeksDtos;
                this.selectWeek = res.data.result.startOfWeek;
                this.selectYear = this.selectWeek.slice(0,4);
                console.log('주간 데이터',this.getWeekly)
              }else{
                alert(res.data.message);
              }
            })
            .catch((res)=>{
              console.error(res);
            })
      },
    // 상단 검색 버튼을 누르면 실행되는 메서드
    async SearchDate(){
      this.HolidayCheck = [false,false,false,false,false,false,false,false,false,false,false,false,false,false]
      this.isDummy = [false,false,false,false,false,false,false,false,false,false,false,false,false,false]
      this.status = [false,false,false,false,false,false,false,false,false,false,false,false,false,false];
    
      this.confirm = [false,false,false,false,false,false,false,false,false,false,false,false,false,false];
      this.approve = [false,false,false,false,false,false,false,false,false,false,false,false,false,false];
      this.ended = [false,false,false,false,false,false,false,false,false,false,false,false,false,false];
      await this.GetServer();

      this.SecondPlanTotalHour = 0;
      this.FirstPlanTotalHour = 0;
      this.FirstPerfTotalHour = 0;
      this.SecondPerfTotalHour = 0;
        
      this.calDate_v2(this.Date)
      this.calDayOfData()
      this.calDayWorkTime_v2()
      this.printPrevWorkType(this.perf)
      this.DistinguishHoliday()
      this.calApprovalTime()
      this.DistinguishStatus()
      this.calTotalWeekTime()
      this.isDummyFunc()
       
      
    },
    //------------------------------------ AXIOS -------------------------------------------
    calNowDate(){
      const date = new Date();
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();

      if(month<10){
        this.selectDay = (year*10000)+(month*100)+day;
      }else{
        this.selectDay = (year*1000)+(month*100)+day;
      }
    },

    calSelectYear(){
        var index = 0;
        for(var i = 0;i<this.searchWeekly.length;i++){
          if(index === 0 ){
            this.searchWeekYear[index] = this.searchWeekly[i].year
            index++;
          }

          var selectyear = 0, datayear = 0;

          selectyear = Number(this.searchWeekYear[index-1])
          datayear=Number(this.searchWeekly[i].year)

          if(selectyear < datayear){
            this.searchWeekYear.push(this.searchWeekly[i].year)
            index++;
          }
        }
      },

      calSelectWeek(){
        for(var i = 0;i<this.searchWeekly.length;i++){
          this.copySearchWeekly.push({
            year: this.searchWeekly[i].year,
            fromdt: this.searchWeekly[i].fromdt,
            content: this.searchWeekly[i].content
          })
        }

        for(var i = 0;i<this.copySearchWeekly.length;i++){
          if(this.selectYear != this.copySearchWeekly[i].year){
            this.copySearchWeekly.splice(i,1);
            i--;
          }
        }
      },
    showViewModal(index){
      var tasknum = 0;
      

      for(var i = 0;i<this.perf.length;i++){
        if(this.Date[index] === this.perf[i].perfDay){
          this.sendDetailInfo[tasknum] = this.perf[i];
          tasknum++;
        }
      }
      this.viewModal = !this.viewModal;
      this.sendDayWorkTime = this.DayWorkTime[index];
    },
    showViewModalEdit(){
      this.editModal = !this.editModal;
    },
    // --------------------------------------- 날짜 데이터 구하는 함수, 윤년 포함해서 한달이 넘어가게 되면 자동으로 반영 -----------
    calDate_v2(date){
      //날짜를 데이터에 인풋
      if(this.selectYear === 0 && this.selectYear%100 != 0 || this.selectYear%400 === 0){
        this.EndDayOfMonth[1] = 29;
      }
      var startMonth = parseInt((this.perf[0].perfDay%10000)/100);
      console.log(startMonth)
      var EndDay = this.EndDayOfMonth[startMonth-1];

      EndDay = parseInt(this.perf[0].perfDay/100)*100+EndDay;
      console.log(EndDay)

      var newMonthDay = 0
      var newDay = 0

      // newDay = + parseInt(EndDay%10000) 
      // newMonthDay = parseInt(EndDay/10000)*10000 + (parseInt(newDay/100)+1)*100 + 1

      // console.log('새로운 달력', newMonthDay)
      var newMonth = 0;
      for(var i = 0;i<this.twoWeek;i++){
        date[i] = this.perf[0].perfDay+i;
        if(date[i] > EndDay){
          newDay = + parseInt(EndDay%10000) 
          newMonthDay = parseInt(EndDay/10000)*10000 + (parseInt(newDay/100)+1)*100 + 1
          date[i] = newMonthDay+newMonth;
          newMonth++;
        }
      }
      //view 데이터에 인풋
      var viewdate = []
      for(var i = 0;i<this.twoWeek;i++){
        viewdate[i] = String(date[i])
        viewdate[i] = viewdate[i].substr(6, 2);
      }
      this.viewDate = viewdate;

    },
    //-----------------------------------------날짜 데이터를 구하는 함수 끝------------------------------
    //--------------------------------하루 당 데이터 구하는 함수 ------------------------------
    calDayOfData(){
      this.departmentName = localStorage.getItem('deptNm')

      var perfindex = 0;

      for(var i = 0; i<this.perf.length;i++){
        if(this.perf[i].seq === 1 || this.perf[i].seq === 0){
          this.DayOfData[perfindex] = this.perf[i]; // seq 가 없을 수도 있으니, 그걸로 예외 처리
          perfindex++;
        }
      }

      var planindex = 0;
      for(var i =0;i<this.plan.length;i++){
        if(this.plan[i].seq === 1 || this.plan[i].seq === 0){
          this.planDayOfDate[planindex] = this.plan[i];
          planindex++
        }
      }
    },
    calTotalWeekTime(){
      var perfTotalHour = 0, planTotalHour = 0;
      for(var i = 0;i<this.planDayOfDate.length;i++){
        if(i<7){
          if(this.planDayOfDate[i].isHoliday === 'N'){
            this.FirstPlanTotalHour += this.planDayOfDate[i].dayHour;
          }
        }else{
          if(this.planDayOfDate[i].isHoliday === 'N'){
            this.SecondPlanTotalHour += this.planDayOfDate[i].dayHour;
          }
        }
      }

      for(var i = 0;i<this.DayOfData.length;i++){
        if(i<7){
          if(this.DayOfData[i].isHoliday === 'N'){
            this.FirstPerfTotalHour += this.DayOfData[i].dayHour;
          }
        }else{
          if(this.DayOfData[i].isHoliday === 'N'){
            this.SecondPerfTotalHour += this.DayOfData[i].dayHour;
          }
        }
      }

    },
    //--------------------------------하루 당 데이터 구하는 함수 끝 ----------------------------

    //--------------------------------하루 업무 시간을 보여주는 함수--------------------------------
    calDayWorkTime_v2(){
      var start_time, end_time;
      var restTime = 100;
      for(var i = 0;i<this.DayOfData.length;i++){
        start_time = this.DayOfData[i].startedHour;
        end_time = (this.DayOfData[i].dayHour*100)+restTime;

        end_time = String(Number(start_time)+end_time);

        this.DayWorkTime[i] = [start_time.slice(0,2),':',start_time.slice(2,4),' ~ ',end_time.slice(0,2),':',end_time.slice(2,4)].join('');

      }
    },
    //--------------------------------하루 업무 시간을 보여주는 함수 끝--------------------------------

    //---------------------------캘린더에 W, P 와 수행시간을 보여주는 함수----------------------------
    printPrevWorkType(perf) {
      let total = this.perf.length;

      for(var i = 0;i<this.twoWeek;i++){
        var task = [];
        for(var j = 0;j<total;j++){
          if(this.Date[i] === perf[j].perfDay){//해당 일자에서
            if(this.perf[j].groupMainId === 'TR001'){
              task.push('W'+perf[j].taskHour+' '+perf[j].workDetail)//group_name이 아닌 기타사항이 들어가야함
            }else if(this.perf[j].groupSubId === 'TR002'){
              task.push('P'+perf[j].taskHour+' '+perf[j].workDetail)//group_name이 아닌 기타사항이 들어가야함
            }
          }
        }
        this.DailyTask[i] = task;
      }
    },
    //------------------------캘린더에 W, P 와 수행시간을 보여주는 함수 끝 ----------------------------
    DistinguishHoliday(){
      for(var i = 0;i<this.DayOfData.length;i++){
        if(this.DayOfData[i].isHoliday === "Y"){
          this.HolidayCheck[i] = true;
          this.isDummy[i] = true
        }else{
          this.HolidayCheck[i] = false;
        }
      }
    },
    calApprovalTime(){
      for(var i = 0;i<this.twoWeek;i++){
        if(this.DayOfData[i].isHoliday === 'N'){
          this.approvalTime += this.DayOfData[i].dayHour;
        }
      }
    },
    DistinguishStatus(){
      for(var i = 0;i<this.twoWeek;i++){
        if(this.DayOfData[i].signStatus === '2'){
          this.confirm[i] = true;
        }else if(this.DayOfData[i].signStatus === '3'){
          this.approve[i] = true;
        }
        if(this.DayOfData[i].isDeadline === '1'){
          this.ended[i] = true;
        }
      }
    },
    //------------------------------content 를 보여줄지 말지에 대한 판단 함수 ---------------------------
      isDummyFunc(){
          for(var i = 0;i<this.DayOfData.length;i++){
            if(this.DayOfData[i].seq === 0){
              this.isDummy[i] = true
            }
          }
          console.log(this.isDummy)
          console.log(this.HolidayCheck);
      },
      showCalenderData(index){
        if(this.HolidayCheck[index] && this.isDummy[index]){//휴일 false, 더미 false일때만
          return false;
        }else if(!(this.HolidayCheck[index]) && !(this.isDummy[index])){
          return true; 
        }else{
          false;
        }
      },
    logout(){
      localStorage.setItem('memberId', '0')
      localStorage.setItem('memberNm','No');
      localStorage.setItem('grade','GEUST');
      localStorage.setItem('deptNm', '무소속');
      this.$router.push('/')
    },


    //------------------------------------------------------------------------ 삭제 요망
    // calDate(startdate){
    //   for(var i = 0;i<14;i++){
    //     this.Date[i] = startdate+i;
    //   }
    //   for(var j = 0;j<this.perf.length;j++) {
    //     if (Date[1]===this.perf[j].perfDay && this.perf[j].seq == 1) {
    //       console.log('check');
    //     }
    //   }
    //
    // },
    // viewDay(date){
    //   var viewdate = []
    //   for(var i = 0;i<date.length;i++){
    //     viewdate[i] = String(date[i])
    //     viewdate[i] = viewdate[i].substr(6, 2);
    //   }
    //   this.viewDate = viewdate;
    // },
    //------------------------------------------------------------------------ 삭제 요망
    //변경사항 -> 시간을 통일해야할 듯



    //----------------------------------------- 삭제 --------------------------------------
    // calDayWorkTime(perf){
    //   var hour, min, restTime = 1, startTime, endTime;
    //   var start = [], end =[];
    //   let daytime = this.Date.length;
    //   let total = this.perf.length;
    //
    //
    //   for(var j = 0;j<daytime;j++) {
    //     for (var i = 0; i < total; i++) {
    //       if (perf[i].seq == 1) {
    //         startTime = perf[i].startedHour;
    //         //String을 시간과 분으로 분리
    //         hour = startTime.substr(0, 2);
    //         min = startTime.substr(2, 4);
    //         //시작 시간에 : 을 붙임
    //         startTime = hour + ':' + min;
    //         //끝나는 hour를 구하기 위해서 총 근로시간과 시작 시간을 더한다, 그리고 휴식 시간까지 더한다.
    //         hour = String(Number(hour) + Number(perf[i].dayHour) + restTime);
    //         //끝나는 시간을 더한다.
    //         endTime = hour + ":" + min;
    //
    //         //각각의 데이터를 push해준다.
    //         if (j === 5 || j === 6 || j === 12 || j === 13) {// 휴일 처럼 데이터가 없을때 골라내는 형식
    //           end[j] = ''
    //           start[j] = ''
    //         } else {
    //           start[j] = startTime;
    //           end[j] = endTime;
    //         }
    //       }
    //       if(start[j] === ''){
    //         this.DayWorkTime[j] = ''
    //       }else{
    //         this.DayWorkTime[j] = start[j] + ' ~ '+end[j];
    //       }
    //     }
    //   }
    // },
    //----------------------------------------- 삭제 --------------------------------------

  },
  components:{
    PerfDetailVue: PerfDetailVue,
    PerfDetailEditVue: PerfDetailEditVue,
  }

}
</script>
<style>
.calendar-interval-top{
  width: 175px;
  height: 10px;
}
.calendar-interval{
  width: 175px;
  height: 180px;
}
.modal-position{
  right: 200px;
  bottom: 40px;
}
</style>