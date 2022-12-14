<!-- 완성 -->
<template>
      <!--검색 부분 -->
      <div class="grow bg-gray-800	rounded-lg ml-2 mt-5">
          <div class="rounded-lg h-16 bg-gray-700 ml-5 flex" style="max-width:1230px">
            <div class="h-8 place-self-center flex">
                <div class="flex">
                    <div class="mt-1.5">
                        <span class="ml-4 text-gray-200">주 시작일</span>
                    </div>
                    <div class="mt-1.5 ml-4">
                        <input v-model="searchDay" @change="SearchDay()" type="date"/>
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
            <div class="text-gray-200 flex mr-8 mt-4" style="max-width:1200px">
                <div class="flex h-8">
                    <div class="mr-2 w-10 bg-emerald-500 rounded-xl"><p class="mt-1">확정</p></div>
                    <div><p class="mt-1">확정</p></div>
                </div>
                <div class="flex ml-3 h-8">
                    <div class="mr-2 w-10 bg-sky-500 rounded-xl"><p class="mt-1">PM</p></div>
                    <div><p class="mt-1">PM승인</p></div>
                </div>
                <div class="flex ml-3 h-8">
                    <div class="mr-2 w-10 bg-fuchsia-500 rounded-xl"><p class="mt-1">팀장</p></div>
                    <div><p class="mt-1">팀장승인</p></div>
                </div>
                <div class="flex ml-3 h-8">
                    <div class="mr-2 w-12 bg-indigo-500 rounded-xl"><p class="mt-1">사업부</p></div>
                    <div><p class="mt-1">사업부승인</p></div>
                </div>
                <div class="flex ml-3 h-8">
                    <div class="mr-2 w-10 bg-rose-500 rounded-xl"><p class="mt-1">마감</p></div>
                    <div><p class="mt-1">마감</p></div>
                </div>
            </div>
              <button @click="SearchDate()" class="w-10 h-8 place-self-center mr-5 text-gray-200 rounded-lg bg-emerald-700 hover:bg-emerald-600 active:bg-emerald-500 focus:outline-none ">검색</button>
          </div>
          <div class="flex text-gray-200" style="max-width:1265px">
            <div class="ml-7 mt-7 text-xl">
                <p>{{departmentName}}</p>
            </div>
            <div class="grow"></div>
            <div class="flex mt-7">
                <button style="width: 200px; height: 40px;" class="mr-4 bg-orange-500 rounded-lg hover:bg-orange-600 active:bg-orange-700 focus:outline-none"><p class="mt-0.5 ml-0.5">미승인PM SMS전송</p></button>
                <button style="width: 100px; height: 40px;" @click="CancelApprovalFunc()" class="mr-4 bg-red-500	w-16 rounded-lg	hover:bg-red-600 active:bg-red-700 focus:outline-none"><p class="mt-0.5 ml-0.5">승인취소</p></button>
                <button style="width: 50px; height: 40px;" @click="ResetApprovalFunc()" class="mr-4 bg-yellow-500 w-10 rounded-lg	hover:bg-yellow-600 active:bg-yellow-700 focus:outline-none"><p class="mt-0.5 ml-0.5">반려</p></button>
                <button style="width: 50px; height: 40px;" @click="ConfirmApprovalFunc()" class="mr-4 bg-emerald-500	w-10 rounded-lg	hover:bg-emerald-600 active:bg-emerald-700 focus:outline-none"><p class="mt-0.5 ml-0.5">승인</p></button>
            </div>
          </div>
          <!-- 상단 검색 부분 끝 -->
          
          <!-- 컨텐츠 부분 -->
          <div class="ml-5 mt-5 text-gray-200">
            <!-- 표 만들기 -->
            <!-- 상단 메뉴바 -->
            <div class="bg-gray-700 h-16 rounded-tl-lg rounded-tr-lg" style="max-width:1230px">
              <div class="flex">
                <div class="pt-2 border-r" style="width:180px;"><p class="mt-3">구분</p></div>
                <div class="pt-2 main-interval border-r" ><p>Mon <span><input v-model="checkDate" :value="SendDate[0]" type="checkbox"></span></p><p>{{viewDate[0]}}</p></div><!--0~6번 째로 해당 주차의 status를 구분한다.-->
                <div class="pt-2 main-interval border-r" ><p>Tue <span><input v-model="checkDate" :value="SendDate[1]" type="checkbox"></span></p><p>{{ viewDate[1] }}</p></div>
                <div class="pt-2 main-interval border-r" ><p>Web <span><input v-model="checkDate" :value="SendDate[2]" type="checkbox"></span></p><p>{{ viewDate[2] }}</p></div>
                <div class="pt-2 main-interval border-r"><p>Thu <span><input v-model="checkDate" :value="SendDate[3]" type="checkbox"></span></p><p>{{ viewDate[3] }}</p></div>
                <div class="pt-2 main-interval border-r" ><p>Fri <span><input v-model="checkDate" :value="SendDate[4]" type="checkbox"></span></p><p>{{ viewDate[4] }}</p></div>
                <div class="pt-2 main-interval border-r"><p>Sat <span><input v-model="checkDate" :value="SendDate[5]" type="checkbox"></span></p><p>{{ viewDate[5] }}</p></div>
                <div class="pt-2 main-interval border-r"><p>Sun <span><input v-model="checkDate" :value="SendDate[6]" type="checkbox"></span></p><p>{{ viewDate[6] }}</p></div>
                <div class="pt-2 all" style="width: 70px;"><p class="mt-3">All <span><input type="checkbox" v-model="checkDate" :value="SendDate" ></span></p></div>
              </div>
            </div>

            
           
            <!-- 서브 메뉴 -->
            <div class="bg-gray-700 h-10 border-t" style="max-width:1230px" >
                <div class="flex">
                    <div class="border-r flex" style="width:180px;">
                        <div class=" pt-2" style="width:50%"><p>팀원</p></div>
                        <div class=" pt-2 border-l" style="width:50%"><p>승인제외</p></div>
                    </div>
                    <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">시간</p></div>
                        <div class=" hour-interval border-l"><p class="pt-1">상태</p></div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">시간</p></div>
                        <div class=" hour-interval border-l"><p class="pt-1">상태</p></div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">시간</p></div>
                        <div class=" hour-interval border-l"><p class="pt-1">상태</p></div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval "><p class="pt-1">시간</p></div>
                        <div class=" hour-interval border-l"><p class="pt-1">상태</p></div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">시간</p></div>
                        <div class=" hour-interval border-l"><p class="pt-1">상태</p></div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">시간</p></div>
                        <div class=" hour-interval border-l"><p class="pt-1">상태</p></div>
                    </div>
                    <div class="flex sub-interval" >
                        <div class=" hour-interval"><p class="pt-1">시간</p></div>
                        <div class=" hour-interval border-l"><p class="pt-1">상태</p></div>
                    </div>
                    <div class="flex">
                      <div class="border-l " style="width: 71px;"><p class="pt-2 pl-1">전체시간</p></div>
                    </div>
                </div>
            <!-- 실제 데이터 인풋 -->
            <div class="member-view">
              <div class="bg-gray-600 h-10 border-t" style="max-width:1230px" v-for="(member,index) in viewApprovalTable">
                <div class="flex">
                    <div class="border-r flex" style="width:180px;">
                        <div class=" pt-2" style="width:50%"><p>{{ member[0].memberNm }}</p></div>
                        <div class=" pt-2 border-l" style="width:50%"><input v-model="checkOutMember" :value="teamMember[index]" type="checkbox"> </div><!--멤버별 승인 제외 index로 구분한다.-->
                    </div>
                    <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">{{ member[0].dayHour }}</p></div>
                        <div class=" hour-interval border-l">
                            <div v-if="blindStatus(member[0].isDeadline)" :class="{blind:statusFunc(member[0].signStatus)}, confirmCheck(member[0].signStatus)" class="w-10 rounded-xl state-interval">
                              <p class="mt-1">{{ confirmText[member[0].signStatus] }}</p>
                            </div>
                            <div v-if="!(blindStatus(member[0].isDeadline))" class="w-10 bg-rose-500 rounded-xl state-interval">
                              <p class="mt-1">마감</p>
                            </div>
                        </div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">{{ member[1].dayHour }}</p></div>
                        <div class=" hour-interval border-l">
                            <div v-if="blindStatus(member[1].isDeadline)" :class="{blind:statusFunc(member[1].signStatus)}, confirmCheck(member[1].signStatus)" class="w-10 rounded-xl state-interval">
                              <p class="mt-1">{{ confirmText[member[1].signStatus] }}</p>
                            </div>
                            <div v-if="!(blindStatus(member[1].isDeadline))" class="w-10 bg-rose-500 rounded-xl state-interval">
                              <p class="mt-1">마감</p>
                            </div>
                        </div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">{{ member[2].dayHour }}</p></div>
                        <div class=" hour-interval border-l">
                            <div v-if="blindStatus(member[2].isDeadline)" :class="{blind:statusFunc(member[2].signStatus)}, confirmCheck(member[2].signStatus)" class="w-10 rounded-xl state-interval">
                              <p class="mt-1">{{ confirmText[member[2].signStatus] }}</p>
                            </div>
                            <div v-if="!(blindStatus(member[2].isDeadline))" class="w-10 bg-rose-500 rounded-xl state-interval">
                              <p class="mt-1">마감</p>
                            </div>
                        </div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval "><p class="pt-1">{{ member[3].dayHour }}</p></div>
                        <div class=" hour-interval border-l">
                            <div v-if="blindStatus(member[3].isDeadline)" :class="{blind:statusFunc(member[3].signStatus)}, confirmCheck(member[3].signStatus)" class="w-10  rounded-xl state-interval">
                              <p class="mt-1">{{ confirmText[member[3].signStatus] }}</p>
                            </div>
                            <div v-if="!(blindStatus(member[3].isDeadline))" class="w-10 bg-rose-500 rounded-xl state-interval">
                              <p class="mt-1">마감</p>
                            </div>
                        </div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">{{ member[4].dayHour }}</p></div>
                        <div class=" hour-interval border-l">
                            <div v-if="blindStatus(member[4].isDeadline)" :class="{blind:statusFunc(member[4].signStatus)}, confirmCheck(member[4].signStatus)" class="w-10  rounded-xl state-interval">
                              <p class="mt-1">{{ confirmText[member[4].signStatus] }}</p>
                            </div>
                            <div v-if="!(blindStatus(member[4].isDeadline))" class="w-10 bg-rose-500 rounded-xl state-interval">
                              <p class="mt-1">마감</p>
                            </div>
                        </div>
                    </div>
                     <div class="flex sub-interval border-r" >
                        <div class=" hour-interval"><p class="pt-1">{{ member[5].dayHour }}</p></div>
                        <div class=" hour-interval border-l">
                            <div v-if="blindStatus(member[5].isDeadline)" :class="{blind:statusFunc(member[5].signStatus)}, confirmCheck(member[5].signStatus)" class="w-10  rounded-xl state-interval">
                              <p class="mt-1">{{ confirmText[member[5].signStatus] }}</p>
                            </div>
                            <div v-if="!(blindStatus(member[5].isDeadline))" class="w-10 bg-rose-500 rounded-xl state-interval">
                              <p class="mt-1">마감</p>
                            </div>
                        </div>
                    </div>
                     <div class="flex sub-interval" >
                        <div class=" hour-interval"><p class="pt-1">{{ member[6].dayHour }}</p></div>
                        <div class=" hour-interval border-l">
                            <div v-if="blindStatus(member[6].isDeadline)" :class="{blind:statusFunc(member[6].signStatus)}, confirmCheck(member[6].signStatus)" class="w-10 rounded-xl state-interval">
                              <p class="mt-1">{{ confirmText[member[6].signStatus] }}</p>
                            </div>
                            <div v-if="!(blindStatus(member[6].isDeadline))" class="w-10 bg-rose-500 rounded-xl state-interval">
                              <p class="mt-1">마감</p>
                            </div>
                        </div>
                    </div>
                    <div class="flex" >
                      <div class="border-l" style="width: 71px;"><p class="pt-2 pl-1">{{ member[0].totalTime }}</p></div>
                    </div>
                </div>
              </div>
            </div>
            </div>
          </div>
      </div>

</template>
<script>
import { isGloballyWhitelisted } from "@vue/shared";
import axios from "axios";
import approval from '../assets/approvalData.json';
export default {
  name: 'CompanyProject',
  data(){
    return{
      //axios로 인해서 받은 데이터
      searchDay: '',
      responseCode: 0,
      backMessage: '',

      selectDate: 0,
      //원본 데이터 파일
      approval: [], // ----------------------------------------------> axios get 요청 데이터를 받는 곳
      //화면이 보여주는 영역
      departmentName:'',
      Date: [],
      viewDate: [],
      teamMember: [],
      viewApprovalTable: [],
      confirmText: ['','','확정','팀장'],

      //checking 데이터
      SendDate:[],
      checkOutMember:[],
      checkDate: [],
      checkDeadline:[],
      checkStatus: [],

      //보내주는 데이터 셋
      CopyApprovalDate: [],
      sendData:
        {
          "memberIds": [],
          "days": []
        },
      copySendData:{
        "memberIds": [],
        "days":[]
      },
      ended:[],
      patchData: {
        "memberIds":[],
        "days" : [],
        "startDate": ''
      }


    }
  },
  async mounted() {
    this.SetNowDate();
    await this.GetApprove();
    this.SetCopyData()
    this.calDate(this.CopyApprovalDate[0].perfDay);
    this.extractionDeadline();
    this.calTeamMember(this.CopyApprovalDate);
    this.viewApprovalTableFunc(this.CopyApprovalDate);
  },
  methods:{
    //------------------------------------------- 마운트 되기 전에 실행되야하는 함수 --------------------------------
    async GetApprove(){
      console.log(this.selectDate)
      await axios.get('/api/approvals',{
        params:{
          day: String(this.selectDate),
        },withCredentials:true})
          .then((res)=>{
            console.log(res.data.result)
            this.responseCode = res.data.code;
            this.backMessage = res.data.message;
            this.approval = res.data.result;
            if(this.responseCode != 1000){
              alert('로그인 후 이용해주세요')
              alert(this.backMessage);
              this.logout();
            }
          })
          .catch((res)=>{
            console.error(res);
          })


    },
    SetNowDate(){
      const date = new Date();

      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();

      if(month < 10){
        this.selectDate = (year*10000)+(month*100)+day;
      }else{
        this.selectDate = (year*1000)+(month*100)+day;
      }
      

    },
    SetCopyData(){
      this.departmentName = localStorage.getItem('deptNm')
      for(var i = 0;i<this.approval.length;i++){
        this.CopyApprovalDate.push({
          perfDay : this.approval[i].perfDay,
          memberId: this.approval[i].memberId,
          memberNm : this.approval[i].memberNm,
          signStatus: this.approval[i].signStatus,
          totalTime : this.approval[i].totalTime,
          dayHour: this.approval[i].dayHour,
          isHoliday: this.approval[i].isHoliday,
          isDeadline: this.approval[i].isDeadline
        })
      }
    },
    //------------------------------------------- 데이터를 화면에 출력해주는 함수들 --------------------------------
    async SearchDate(){
      await this.SearchDay();
    },
    async SearchDay(){
      var sendDate = String(this.searchDay);
      sendDate = sendDate.replaceAll('-','')
      sendDate = Number(sendDate);
      this.selectDate = sendDate;

      this.CopyApprovalDate = []

      console.log(this.selectDate)
      await this.GetApprove();
      this.SetCopyData()
      this.calDate(this.CopyApprovalDate[0].perfDay);
      this.extractionDeadline();
      this.calTeamMember(this.CopyApprovalDate);
      this.viewApprovalTableFunc(this.CopyApprovalDate);

    },
    calDate(startdate){
      console.log(this.CopyApprovalDate)
      var Deadline =[]
      for(var i = 0;i<this.CopyApprovalDate.length;i++){
        if(this.CopyApprovalDate[0].memberId === this.CopyApprovalDate[i].memberId){
          Deadline.push(this.CopyApprovalDate[i].isDeadline);
        }
      }
      console.log(Deadline)
     for(var i = 0;i<7;i++){
       this.Date[i] = startdate+i;
       this.SendDate.push({
         index: i,
         date: String(this.Date[i]),
         isDeadline: Deadline[i]
       })
     }

      var date = [...this.Date];

      for(var i = 0;i<7;i++){
        date[i] = String(date[i]);

        this.viewDate[i] = [date[i].slice(4,6),'.',date[i].slice(6,8)].join('');
      }

   },
    //-> 구조를 변경해야함,, 어쩔 수 없음
    //멤버를 멤버 리스트에 넣는 작업
    calTeamMember(approval){
     for(var i = 0;i<approval.length;i++){
      if(this.Date[0] === approval[i].perfDay){
        this.teamMember.push(approval[i].memberId)
      }
     }
     this.sendData.memberIds = this.teamMember
    },//화면에 멤버 리스트가 뽑히는 부분
    viewApprovalTableFunc(approval){
      for(var i = 0; i<this.teamMember.length;i++){
        var arr = []
        for(var j = 0; j< approval.length;j++){
          if(this.teamMember[i] === approval[j].memberId){
            arr.push(approval[j])
          }
        }
        this.viewApprovalTable[i] = arr
      }

    },
    statusFunc(status){
      //색상이랑, p태그만 교체하면됨
      if(status === '1'){
        return true
      }else{
        return false
      }
    },
    confirmCheck(status){
      return{
        'bg-green-500': status === '2',
        'bg-fuchsia-500':status === '3',
      }
    },
    //deadline 추출하기
    extractionDeadline(){
      var index = 0
      for(var i = 0;i<this.CopyApprovalDate.length;i++){
        if(this.CopyApprovalDate[0].memberId === this.CopyApprovalDate[i].memberId){
          this.checkDeadline.push({
            id: index,
            perfDay: String(this.CopyApprovalDate[i].perfDay),
            isDeadline:this.CopyApprovalDate[i].isDeadline,
          });
          index++
        }
      }
    },
    blindStatus(deadline){
      if(deadline === '1'){
        return false
      }else{
        return true
      }
    },
    //--------------------------------------------------- 데이터를 수집하는 함수 --------------------------------
    async SendApprovalData(NowStatus, NextStatus){

      this.copySendData.memberIds = [...this.sendData.memberIds];
      this.copySendData.days = [...this.sendData.days];

      console.log(this.copySendData)

      this.copySendData.days = [...this.checkDate];
      const member = [...this.copySendData.memberIds];
      this.copySendData.memberIds = [...member.filter(x=>!this.checkOutMember.includes(x))] //승인 제외 reverse를 구하는 함수

      console.log(this.copySendData)

      if(this.copySendData.days.length === 0){
        alert('날짜를 선택 해주세요')
        return false;
      }
      if(this.copySendData.memberIds.length === 0){
        alert('팀원을 선택 해주세요')
        return false;
      }
      console.log(this.checkDate)
      for(var i = 0;i<this.checkDate.length;i++){
        if(this.checkDate[i].isDeadline === '1'){
          alert([this.checkDate[i].date.slice(0,4),'년 ',this.checkDate[i].date.slice(4,6),'월 ',this.checkDate[i].date.slice(6,8),'일'].join('')+'은 마감일 이므로 승인이 불가능합니다.')
          return false;
        }
      }

      var totalMember = this.copySendData.memberIds.length;


      for(var j = 0;j<totalMember;j++){
        var memberData = []
        for(var i = 0;i<this.CopyApprovalDate.length;i++){
          if(this.copySendData.memberIds[j] === this.CopyApprovalDate[i].memberId){
            memberData.push({
              memberId: this.CopyApprovalDate[i].memberId,
              signStatus: this.CopyApprovalDate[i].signStatus
            })
          }
        }
        this.checkStatus.push(memberData);
      }

      if(NowStatus === '2' && NextStatus === '3'){
        for(var i = 0;i<totalMember;i++){
          for(var j = 0;j<this.checkDate.length;j++){
            if(this.checkStatus[i][this.checkDate[j].index].signStatus === '1'){
              alert('확정되지 않는 실적입니다.')
              return c;
            }else if(this.checkStatus[i][this.checkDate[j].index].signStatus === '3'){
              alert('이미 처리된 실적입니다.')
              return false;
            }
          }
        }
      }else if(NowStatus === '3' && NextStatus === '1'){
        for(var i = 0;i<totalMember;i++){
          for(var j = 0;j<this.checkDate.length;j++){
            if(this.checkStatus[i][this.checkDate[j].index].signStatus === '1' || this.checkStatus[i][this.checkDate[j].index].signStatus === '2'){
              alert('승인되지 않은 데이터 입니다.')
              return false;
            }
          }
        }
      }else if(NowStatus === '2' && NextStatus === '1'){
        for(var i = 0;i<totalMember;i++){
          for(var j = 0;j<this.checkDate.length;j++){
            if(this.checkStatus[i][this.checkDate[j].index].signStatus === '2' || this.checkStatus[i][this.checkDate[j].index].signStatus === '1'){
              alert('반려 할 수 없는 데이터 입니다.')
              return false;
            }
          }
        }
      }
      return true;
      // memberid를 비교해서 결과물을 추출해본다.


      



      //마감, signStatus, memberId, perfDay



      // this.axios.post('/test', this.sendData)
      //     .then((res)=>{
      //       console.log(res.data)
      //     })
      //     .catch((error)=>{
      //       console.error(error)
      //     })
    },
    async ConfirmApprovalFunc(){
      //시작 날짜도 보내야함
      const SendYN = await this.SendApprovalData('2','3')
      if(!SendYN){
        console.log('정상적으로 튕김')
        return;
      }
      

      var days = [],memberIds = [];

      for(var i = 0;i<this.copySendData.days.length;i++){
        days.push(this.copySendData.days[i].date);
      }
      memberIds = [...this.copySendData.memberIds];
      console.log(days,memberIds)
      this.patchData.memberIds = memberIds;
      this.patchData.days = days;
      this.patchData.startDate = String(this.CopyApprovalDate[0].perfDay);

      console.log(this.patchData);

      await axios.patch('/api/approvals', this.patchData,{withCredentials:true})
          .then((res)=>{
            this.responseCode = res.data.code;
            this.backMessage = res.data.message;
            if(this.responseCode === 1000){
              alert('승인 처리가 완료 되었습니다.')
              this.$router.go(0);
            }else{
              alert(this.backMessage)
            }
          })
          .catch((res)=>{
            console.error(res);
          });
    },
    async CancelApprovalFunc(){
      
      const SendYN = await this.SendApprovalData('3','1')

      if(!SendYN){
        console.log('정상적으로 튕김')
        return;
      }

      var days = [],memberIds = [];

      for(var i = 0;i<this.copySendData.days.length;i++){
        days.push(this.copySendData.days[i].date);
      }
      memberIds = [...this.copySendData.memberIds];
      console.log(days,memberIds)
      this.patchData.memberIds = memberIds;
      this.patchData.days = days;
      this.patchData.startDate = String(this.CopyApprovalDate[0].perfDay);

      console.log(this.patchData);

      await axios.patch('/api/approvals/cancel',this.patchData,{withCredentials:true})
          .then((res)=>{
            if(res.data.code === 1000){
              alert('승인 취소가 완료 되었습니다.')
              this.$router.go(0)
            }else{
              alert(res.data.message);
            }
          })
          .catch((res)=>{
            console.error(res);
          })
    },
    async ResetApprovalFunc(){

      const SendYN = await this.SendApprovalData('2','1')

      if(!SendYN){
        console.log('정상적으로 튕김')
        return;
      }

      var days = [],memberIds = [];

      for(var i = 0;i<this.copySendData.days.length;i++){
        days.push(this.copySendData.days[i].date);
      }
      memberIds = [...this.copySendData.memberIds];
      console.log(days,memberIds)
      this.patchData.memberIds = memberIds;
      this.patchData.days = days;
      this.patchData.startDate = String(this.CopyApprovalDate[0].perfDay);

      console.log(this.patchData);

      await axios.patch('/api/approvals/reset',this.patchData,{withCredentials: true})
          .then((res)=>{
            if(res.data.code === 1000){
              alert('승인이 반려되었습니다.')
            }else{
              alert(res.data.message);
            }
          })
          .catch((res)=>{
            console.error(res);
          })
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
.max-width{
    max-width: 1200px;
}
.main-interval{
  width: 140px;
  height: 65px;
}
.sub-interval{
    width: 140px;
    height: 40px;
}
.hour-interval{
    width: 70px;
    height: 40px;
    padding-top: 3px;
}
.state-interval{
    margin-top: 20px;
    margin:auto;
}
.blind{
  display: none;
}
.member-view{
  width: 1240px;
  height: 500px;
  overflow-y: scroll;
  overflow-x: hidden;
}
.member-view::-webkit-scrollbar{
  width: 10px;
  border-radius: 5px;
  background-color: rgb(30 41 59);
}
.member-view::-webkit-scrollbar-thumb{
  border-radius: 5px;
  background-color: rgb(100 116 139);
}
</style>
