<!--

마감 관리

-->
<template>
    <div>
         <!--검색 부분 -->
      <div class="grow bg-gray-800	rounded-lg ml-2 mt-5">
          <div class="rounded-lg h-16 bg-gray-700 ml-5 flex" style="max-width:1230px">
            <div class="h-8 place-self-center flex">
              <div class="flex">
                <div class="mt-1.5">
                  <span class="ml-4 text-gray-200">주 시작일</span>
                </div>
                <div class="mt-1.5 ml-4">
                  <select v-model="selectYear">
                    <option v-for="year in searchYear" :value="year">{{ year }}</option>
                  </select>
                </div>
                <div class="mt-1.5 ml-4">
                  <select v-model="selectMonth">
                    <option v-for="month in searchMonth" :value="month.value">{{ month.text }}</option>
                  </select>
                </div>
              </div>
                
                <div class="flex">
                    <div class="mt-1.5">
                        <p class="ml-2 text-gray-200" style="width:100px">조직</p>
                    </div>
                    <div class="mt-1.5">
                            <select>
                                <option>한솔PNS</option>
                                <option>한솔 인티큐브</option>
                            </select>
                        </div>           
                </div>
                

            </div>
            <div class="grow"></div>
            
            <button @click="SearchDate()" class="w-10 h-8 place-self-center mr-5 text-gray-200 rounded-lg bg-green-500 hover:bg-green-600 active:bg-green-700 focus:outline-none ">검색</button>
          </div>
          <div class="flex" style="width:1260px">
            <div class="grow"></div>
            <div class="mt-5">
              <button @click="CancleDeadline()" class="h-8 w-16 calendar-interval-day-check place-self-center mr-5 text-gray-200 rounded-lg bg-sky-500 hover:bg-sky-600 active:bg-sky-700 focus:outline-none ">마감철회</button>
              <button @click="ComfirmDeadline()" class="h-8 w-16 calendar-interval-day-check place-self-center mr-5 text-gray-200 rounded-lg bg-rose-500 hover:bg-rose-600 active:bg-rose-700 focus:outline-none ">마감처리</button>
            </div>
          </div>
          <!-- 컨텐츠 부분 -->
          <div class="ml-5 mt-5 ">
            <!--상단 요일 표시-->
            <div class="flex" style="width:1400px;">
                  <div class="bg-gray-700 text-gray-200 rounded-tl-lg border-slate-600 border-r-2 border-b-2 flex" style="width: 110px;">
                    <p class="ml-7 mt-0.5">전체</p>
                    <input class="pl-3 pt-0.5 week-check-box-size" type="checkbox" v-model="allSelected">
                  </div>
                  <div class="bg-gray-700 text-gray-200 day-menu border-slate-600 border-r-2 border-b-2">
                    <button>Mon</button>
                  </div>
                  <div class="bg-gray-700 text-gray-200 border-slate-600 day-menu border-r-2 border-b-2">
                    <button>Tue</button>
                  </div>
                  <div class="bg-gray-700 text-gray-200 border-slate-600 day-menu border-r-2 border-b-2">
                    <button>Web</button>
                  </div>
                  <div class="bg-gray-700 text-gray-200 border-slate-600 day-menu border-r-2 border-b-2">
                    <button>Thu</button>
                  </div>
                  <div class="bg-gray-700 text-gray-200 border-slate-600 day-menu border-r-2 border-b-2">
                    <button>Fri</button>
                  </div>
                  <div class="bg-gray-700 text-gray-200 border-slate-600 day-menu border-r-2 border-b-2">
                    <button>Sat</button>
                  </div>
                  <div class="bg-gray-700 text-gray-200 rounded-tr-lg border-slate-600 day-menu border-b-2 border-r-2">
                    <button>Sun</button>
                  </div>
            </div>
            <!-- 날짜에 대한 데이터 -->
            <!--1주차-->
            <div v-if="showWeek[0]">
              <div  class="flex" style="width:1400px;">
                <div class="bg-gray-300	 text-gray-200 border-r-2 calendar border-slate-600 border-b-2" style="width: 110px;">
                  <p class="text-black mt-5">1주차</p>
                  <input type="checkbox" class="week-check-box-size" v-model="firstWeekSelected">
                </div>

                <div v-for="(month,first) in calendarData[0]" class="bg-gray-300 text-gray-200  day-menu border-r-2 calendar border-slate-600 flex border-b-2 relative">
                  <div v-if="calendarInfo[0][first]" class="date bg-zinc-500 rounded">
                    <p class="pt-1">{{month.date.slice(6,8)}}</p>
                  </div>
                  <div v-if="calendarInfo[0][first]" class="mt-3 ml-20"><input class="check-box-size" v-model="firstWeek" :value="month" type="checkbox"></div>
                  <div v-if="month.isDeadline === '0' ? false : true" class="absolute bottom-2 left-2 bg-rose-500 w-10 h-7 rounded-xl">
                    <p class="mt-1">마감</p>
                  </div>
                </div>

              </div>
            </div>
            <!--2주차-->
            <div v-if="showWeek[1]">
              <div  class="flex" style="width:1400px;">
                <div class="bg-gray-300	 text-gray-200 border-r-2 calendar border-slate-600 border-b-2" style="width: 110px;">
                  <p class="text-black mt-5">2주차</p>
                  <input type="checkbox" class="week-check-box-size" v-model="secondWeekSelected">
                </div>
                <div v-for="(month,second) in calendarData[1]" class="bg-gray-300 text-gray-200  day-menu border-r-2 calendar border-slate-600 flex border-b-2 relative">
                  <div v-if="calendarInfo[1][second]" class="date bg-zinc-500 rounded">
                    <p class="pt-1">{{month.date.slice(6,8)}}</p>
                  </div>
                  <div v-if="calendarInfo[1][second]" class="mt-3 ml-20"><input v-model="secondWeek" :value="month" class="check-box-size" type="checkbox"></div>
                  <div v-if="month.isDeadline === '0' ? false : true" class="absolute bottom-2 left-2 bg-rose-500 w-10 h-7 rounded-xl">
                    <p class="mt-1">마감</p>
                  </div>
                </div>
              </div>
            </div>
            <!--3주차-->
            <div v-if="showWeek[2]">
              <div  class="flex" style="width:1400px;">
                <div class="bg-gray-300	 text-gray-200 border-r-2 calendar border-slate-600 border-b-2" style="width: 110px;">
                  <p class="text-black mt-5">3주차</p>
                  <input type="checkbox" class="week-check-box-size" v-model="thirdWeekSelected">
                </div>
                <div v-for="(month,three) in calendarData[2]" class="bg-gray-300 text-gray-200  day-menu border-r-2 calendar border-slate-600 flex border-b-2 relative">
                  <div v-if="calendarInfo[2][three]" class="date bg-zinc-500 rounded">
                    <p class="pt-1">{{month.date.slice(6,8)}}</p>
                  </div>
                  <div v-if="calendarInfo[2][three]" class="mt-3 ml-20"><input class="check-box-size" v-model="thirdWeek" :value="month" type="checkbox"></div>
                  <div v-if="month.isDeadline === '0' ? false : true" class="absolute bottom-2 left-2 bg-rose-500 w-10 h-7 rounded-xl">
                    <p class="mt-1">마감</p>
                  </div>
                </div>
              </div>
            </div>
            <!--4주차-->
            <div v-if="showWeek[3]">
              <div  class="flex" style="width:1400px;">
                <div class="bg-gray-300	 text-gray-200 border-r-2 calendar border-slate-600 border-b-2" style="width: 110px;">
                  <p class="text-black mt-5">4주차</p>
                  <input type="checkbox" class="week-check-box-size" v-model="fourthWeekSelected">
                </div>
                <div v-for="(month,four) in calendarData[3]" class="bg-gray-300 text-gray-200  day-menu border-r-2 calendar border-slate-600 flex border-b-2 relative">
                  <div v-if="calendarInfo[3][four]" class="date bg-zinc-500 rounded">
                    <p class="pt-1">{{month.date.slice(6,8)}}</p>
                  </div>
                  <div v-if="calendarInfo[3][four]" class="mt-3 ml-20"><input class="check-box-size" v-model="fourthWeek" :value="month" type="checkbox"></div>
                  <div v-if="month.isDeadline === '0' ? false : true" class="absolute bottom-2 left-2 bg-rose-500 w-10 h-7 rounded-xl">
                    <p class="mt-1">마감</p>
                  </div>
                </div>
              </div>
            </div>
            <!--5주차-->
            <div v-if="showWeek[4]">
              <div  class="flex" style="width:1400px;">
                <div class="bg-gray-300	 text-gray-200 border-r-2 calendar border-slate-600 border-b-2" style="width: 110px;">
                  <p class="text-black mt-5">5주차</p>
                  <input type="checkbox" class="week-check-box-size" v-model="fifthWeekSelected">
                </div>
                <div v-for="(month,five) in calendarData[4]" class="bg-gray-300 text-gray-200  day-menu border-r-2 calendar border-slate-600 flex border-b-2 relative">
                  <div v-if="calendarInfo[4][five]" class="date bg-zinc-500 rounded">
                    <p class="pt-1">{{month.date.slice(6,8)}}</p>
                  </div>
                  <div v-if="calendarInfo[4][five]" class="mt-3 ml-20"><input class="check-box-size" v-model="fifthWeek" :value="month" type="checkbox"></div>
                  <div v-if="month.isDeadline === '0' ? false : true" class="absolute bottom-2 left-2 bg-rose-500 w-10 h-7 rounded-xl">
                    <p class="mt-1">마감</p>
                  </div>
                </div>
              </div>
            </div>
            <!--6주차-->
            <div v-if="showWeek[5]">
              <div  class="flex" style="width:1400px;">
                <div class="bg-gray-300	 text-gray-200 border-r-2 calendar border-slate-600 border-b-2 rounded-bl-lg" style="width: 110px;">
                  <p class="text-black mt-5">6주차</p>
                  <input type="checkbox" class="week-check-box-size" v-model="sixthWeekSelected">
                </div>
                <div v-for="(month, six) in calendarData[5]" class="bg-gray-300 text-gray-200  day-menu border-r-2 calendar border-slate-600 flex border-b-2 relative" :class="{'rounded-br-lg': six === 6}">
                  <div v-if="calendarInfo[5][six]" class="date bg-zinc-500 rounded">
                    <p class="pt-1">{{month.date.slice(6,8)}}</p>
                  </div>
                  <div v-if="calendarInfo[5][six]" class="mt-3 ml-20"><input class="check-box-size" v-model="sixthWeek" :value="month" type="checkbox"></div>
                  <div v-if="month.isDeadline === '0' ? false : true" class="absolute bottom-2 left-2 bg-rose-500 w-10 h-7 rounded-xl">
                    <p class="mt-1">마감</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
      </div>
    </div>
    

</template>


<script>
import deadline from '../assets/deadlineData.json';
import axios from "axios";
import weekly from '../assets/biweekly.json'

export default {
    name: 'DeadlineCalender',
    data(){
        return{
          //상단 날짜 검색에 관한 데이터
          searchWeekly: weekly.twoWeeksDtos,
          searchYear: [],
          searchMonth: [
            {text: '1월', value: '01'},
            {text: '2월', value: '02'},
            {text: '3월', value: '03'},
            {text: '4월', value: '04'},
            {text: '5월', value: '05'},
            {text: '6월', value: '06'},
            {text: '7월', value: '07'},
            {text: '8월', value: '09'},
            {text: '10월', value: '10'},
            {text: '11월', value: '11'},
            {text: '12월', value: '12'}],
          selectMonth: '',
          selectYear: weekly.startOfWeek.slice(0,4),

          //axios로 인해서 받은 데이터
          getDead : [],
          responseCode: 0,
          backMessage: '',

          deadline: [], // ----------------------------------------------------> axios get 요청을 받는 변수 부분

          setCalenderTime : 0,
          //실제 캘린더에 적용할 데이터들
          processingCalenderData : [],
          calendarData : [],
          calendarInfo: [],
          showWeek:[true, true, true, true, true, true],
          selectWeek: [false, false, false, false, false],

          //주별, 달 전체 선택에 대한 변수
          firstWeekData: [],
          secondWeekData: [],
          thirdWeekData: [],
          fourthWeekData: [],
          fifthWeekData: [],
          sixthWeekData: [],
          allData:[],
          //보내는 데이터
          firstWeek:[],
          secondWeek:[],
          thirdWeek:[],
          fourthWeek:[],
          fifthWeek:[],
          sixthWeek:[],

          sendData:[],
        }
    },
  async mounted() {
    this.SetNowDate();

    await this.GetWeekData();
    this.calSelectYear();
    await this.getDeadline();
    
    this.SetCalender()

    this.SetCalenderData()
    this.SetCalenderInfo()
    this.setWeekAndAllSelect()

    },
  methods: {
    //------------------------------------ AXIOS -------------------------------------------
    async getDeadline(){
        await axios.get('/api/deadline',{
          params:{
            year: this.selectYear,
            month: this.selectMonth,
          }, withCredentials:true
        })
            .then((res)=>{
              this.deadline = res.data.result;
              console.log(res)
              console.log(this.deadline)
              if(res.data.code != 1000){
                alert(res.data.message);
                this.logout();
              }
            })
            .catch((res)=>{
              console.error(res);
            })
      },
    async GetWeekData(){
      await axios.get('/api/biweekly',{withCredentials: true})
          .then((res)=>{
            if(res.data.code === 1000){
              this.searchWeekly = res.data.result.twoWeeksDtos;
              console.log(this.searchWeekly)
            }else{
              alert(res.data.message);
            }
          })
          .catch((res)=>{
            console.error(res);
          })
    },
    async SearchDate(){
      this.calendarData=[];
      this.processingCalenderData =[];
      this.firstWeekData = []
      this.secondWeekData = []
      this.thirdWeekData = []
      this.fourthWeekData = []
      this.fifthWeekData = []
      this.sixthWeekData = []
      this.allData = []

      await this.GetWeekData();
      this.calSelectYear();
      await this.getDeadline();
      
      this.SetCalender()

      this.SetCalenderData()
      this.SetCalenderInfo()
      console.log('안넘어감?')
      this.setWeekAndAllSelect()

      console.log(this.calendarData)
    },
    //------------------------------------ AXIOS -------------------------------------------
    SetNowDate(){
      const date = new Date();

      const year = date.getFullYear();
      const month = date.getMonth() + 1;

      this.selectYear = String(year)
      if(month / 10 < 1){
        this.selectMonth = '0'+String(month);
      }else{
        this.selectMonth = String(month);
      }

    },
    calSelectYear(){
      var index = 0;
      for(var i = 0;i<this.searchWeekly.length;i++){
        if(index === 0 ){
          this.searchYear[index] = this.searchWeekly[i].year
          index++;
        }

        var selectyear = 0, datayear = 0;

        selectyear = Number(this.searchYear[index-1])
        datayear=Number(this.searchWeekly[i].year)

        if(selectyear < datayear){
          this.searchYear.push(this.searchWeekly[i].year)
          index++;
        }
      }
    },
    SetCalender() {
      // 1 > 일요일, 2 > 월요일, 3 > 화요일 --->
      //if => 1이면, +7로 설정 후에 단체로 -2를 한다.
      //원본 값이 변경되는 현상이 발생함
      var calender = []

      for(var i = 0;i<this.deadline.length;i++){
        calender.push({
          deadlineDay : this.deadline[i].deadlineDay,
          dayOfTheWeek: this.deadline[i].dayOfTheWeek,
          isDeadline : this.deadline[i].isDeadline
        })
      }

      for (var i = 0; i < calender.length; i++) {
        calender[i].dayOfTheWeek -= 1;
      }

      // for (var i = 0; i < calender.length; i++) {
      //   if (calender[i].dayOfTheWeek === 1) {
      //     calender[i].dayOfTheWeek += 7;
      //   }
      //   calender[i].dayOfTheWeek -= 2;
      // }

      for(var i = 0;i<calender.length;i++){
        this.processingCalenderData.push({
          deadlineDay: calender[i].deadlineDay,
          dayOfTheWeek: calender[i].dayOfTheWeek,
          isDeadline : calender[i].isDeadline
        })
      }
      console.log(calender)
      console.log('결과')
      console.log(this.processingCalenderData)

    },
    SetCalenderData(){
      var calender = [...this.processingCalenderData];
      var DeadLineMonth = []
      var index = 0

      end: for (var i = 0; i < 6; i++) {
        var week = []
        for (var j = 0; j < 7; j++) {
          if( index < calender.length){
            if (this.processingCalenderData[index].dayOfTheWeek === j) {
              week.push({
                date: String(this.processingCalenderData[index].deadlineDay),
                isDeadline: this.processingCalenderData[index].isDeadline,
              })
              index++;
            }else{
              week.push({
                date : '0',
                isDeadline: '0',
              })
            }
          }else{
            week.push({
              date: '0',
              isDeadline : '0',
            })
          }
        }
        DeadLineMonth[i] = week;
      }

      this.calendarData = [...DeadLineMonth];
      console.log(this.calendarData)
    },
    SetCalenderInfo() {
      for (var i = 0; i < 6; i++) {
        var calenderArr = []
        var clearWeek = 0
        for (var j = 0; j < 7; j++) {
          if(clearWeek === 6){
            this.showWeek[i] = false
          }
          if(this.calendarData[i].length === 0){
            clearWeek = 6
          }else{
             if(this.calendarData[i][j].date === '0') {
              console.log('데이터가 0인경우',i,j)
              calenderArr.push(false)
              clearWeek++;
            } else {
              calenderArr.push(true)
            }
          }
         

        }
        this.calendarInfo[i] = calenderArr
      }
    },
    setWeekAndAllSelect(){
      
      for(var i = 0;i<7;i++){
        if(this.showWeek[0]){
          if(this.calendarData[0][i].date != '0'){
            this.firstWeekData.push(this.calendarData[0][i])
          }
        }
      }
      for(var i = 0;i<7;i++){
        if(this.showWeek[1]){
          if(this.calendarData[1][i].date != '0'){
            this.secondWeekData.push(this.calendarData[1][i])
          }
        }
      }
      for(var i = 0;i<7;i++){
        if(this.showWeek[2]){
          if(this.calendarData[2][i].date != '0'){
            this.thirdWeekData.push(this.calendarData[2][i])
          }
        }
      }
      for(var i = 0;i<7;i++){
        if(this.showWeek[3]){
          if(this.calendarData[3][i].date != '0'){
            this.fourthWeekData.push(this.calendarData[3][i])
          }
        }
        
      }for(var i = 0;i<7;i++){
        if(this.showWeek[4]){
           if(this.calendarData[4][i].date != '0'){
            this.fifthWeekData.push(this.calendarData[4][i])
          }
        }
      }for(var i = 0;i<7;i++){
        if(this.showWeek[5]){
          if(this.calendarData[5][i].date != '0'){
            this.sixthWeekData.push(this.calendarData[5][i]);
          }
        }
      }
      this.allData.push(this.firstWeekData);
      this.allData.push(this.secondWeekData);
      this.allData.push(this.thirdWeekData);
      this.allData.push(this.fourthWeekData);
      this.allData.push(this.fifithWeekData);
      this.allData.push(this.sixthWeekData);
      console.log(this.allData)
    },
    arrangementSendData(){
      this.sendData.push(...this.firstWeek)
      this.sendData.push(...this.secondWeek);
      this.sendData.push(...this.thirdWeek);
      this.sendData.push(...this.fourthWeek);
      if(this.showWeek[4]){
        this.sendData.push(...this.fifthWeek);
      }
      if(this.showWeek[5]){
        this.sendData.push(...this.sixthWeek);
      }
      console.log(this.sendData)
    },
    //------------------------------------------------------axios post 요청-------------------------------------------------------
    async CancleDeadline(){
      this.arrangementSendData();
    

      var cancleData = {
        days:[]
      }
      for(var i = 0;i<this.sendData.length;i++){
        if(this.sendData[i].isDeadline === '0') {
          var warningDate = [this.sendData[i].date.slice(4, 6), '월', this.sendData[i].date.slice(6, 8), '일'].join('')
          alert(warningDate + ' 은 마감 취소 할 수 없습니다.');
          return;
        }else{
          console.log('push')
          cancleData.days.push(this.sendData[i].date)
        }
      }
      console.log(this.sendData)

      await axios.patch('/api/deadline',cancleData,{withCredentials:true})
      .then((res)=>{
        if(res.data.code === 1000){
          alert('마감 철회가 완료되었습니다.')
          this.$router.go(0)
        }else{
          alert(res.data.message);
        }
      })
      .catch((res)=>{
        console.error(res);
      })
      console.log(cancleData)

      this.$forceUpdate();

    },

    async ComfirmDeadline(){
      this.arrangementSendData();

      var confrimData = {
        days:[]
      }
      for(var i = 0;i<this.sendData.length;i++){
        if(this.sendData[i].isDeadline === '1') {
          var warningDate = [this.sendData[i].date.slice(4, 6), '월', this.sendData[i].date.slice(6, 8), '일'].join('')
          alert(warningDate + ' 은 이미 마감된 날짜 입니다.');
          return;
        }
        confrimData.days.push(this.sendData[i].date)
      }
      await axios.patch('/api/deadline',confrimData,{withCredentials:true})
      .then((res)=>{
        if(res.data.code === 1000){
          alert('마감 처리가 완료되었습니다.')
          this.$router.go(0)
        }else{
          alert(res.data.message);
        }
      })
      .catch((res)=>{
        console.error(res)
      })
      console.log(confrimData)

      
    },
    logout(){
      localStorage.setItem('memberId', '0')
      localStorage.setItem('memberNm','No');
      localStorage.setItem('grade','GEUST');
      localStorage.setItem('deptNm', '무소속');
      this.$router.push('/')
    },
  },
  // -------------------------------------- 해당 부분의 로직 수정 요망 --------------------------------
  computed:{
    firstWeekSelected:{
      get: function (){
      },
      //setter
      set: function (e){
        return this.firstWeek = e ?  this.firstWeekData: [];
      },
    },
    secondWeekSelected:{
      get: function (){
      },
      //setter
      set: function (e){
        return this.secondWeek = e ?  this.secondWeekData: [];
      },
    },
    thirdWeekSelected:{
      get: function (){
      },
      //setter
      set: function (e){
        return this.thirdWeek = e ?  this.thirdWeekData: [];
      },
    },
    fourthWeekSelected:{
      get: function (){
      },
      //setter
      set: function (e){
        return this.fourthWeek = e ?  this.fourthWeekData: [];
      },
    },
    fifthWeekSelected:{
      get: function (){
      },
      //setter
      set: function (e){
        return this.fifthWeek = e ?  this.fifthWeekData: [];
      },
    },
    sixthWeekSelected:{
      get: function (){
      },
      //setter
      set: function (e){
        return this.sixthWeek = e ?  this.sixthWeekData: [];
      },
    },
    allSelected:{
      get: function (){
      },
      set: function (e){
       if(e){
        this.firstWeek = this.firstWeekData;
        this.secondWeek = this.secondWeekData;
        this.thirdWeek = this.thirdWeekData;
        this.fourthWeek = this.fourthWeekData;
        if(this.showWeek[4]){
          this.fifthWeek = this.fifthWeekData;
        }
        if(this.showWeek[5]){
          this.sixthWeek = this.sixthWeekData;
        }
       }else{
        this.firstWeek = []
        this.secondWeek = []
        this.thirdWeek = [];
        this.fourthWeek = [];
        if(this.showWeek[4]){
          this.fifthWeek = []
        }
        if(this.showWeek[5]){
          this.sixthWeek = []
        }
       }
       console.log(this.firstWeek)
      }
    },

  },
}
</script>
<style>
.day-menu{
  width: 160px;
}
.date{
  margin-top: 10px;
  margin-left: 10px;
  width: 30px;
  height: 30px;
}
.calendar{
  height: 95px;
}
.check-box-size{
  width: 30px;
  height: 30px;
}
.week-check-box-size{
  width: 20px;
  height: 20px;
}
</style>