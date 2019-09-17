export default {
    title : {
        text: '企业对外投资分布图'
    },
    backgroundColor:"#fff",
    tooltip : {
        trigger: 'item'
    },
    // legend: {
    //     orient: 'vertical',
    //     x:'left',
    //     data:['iphoneX']
    // },
    dataRange: {
        min: 0,
        max: 2500,
        x: 'left',
        y: 'bottom',
        text:['高','低'],           
        calculable : true
    },
    toolbox: {
        show: true,
        orient : 'vertical',
        x: 'right',
        y: 'center',
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    roamController: {
        show: true,
        x: 'right',
        mapTypeControl: {
            'china': true
        }
    },
    series : [
        {
            name: '投资额度',
            type: 'map',
            mapType: 'china',
            roam: false,
            itemStyle:{
                normal:{label:{show:false}},
                emphasis:{label:{show:true}}
            },
            data:[
                {name: '北京',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '天津',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '上海',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '重庆',value:0,itemStyle:{color:'transparent'}},
                {name: '河北',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '河南',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '云南',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '辽宁',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '黑龙江',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '湖南',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '安徽',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '山东',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '新疆',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '江苏',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '浙江',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '江西',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '湖北',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '广西',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '甘肃',value: Math.round(Math.random()*100),itemStyle:{color:'transparent'}},
                {name: '山西',value:"",itemStyle:{color:'transparent'}},
                {name: '内蒙古',value: "",itemStyle:{color:'transparent'}},
                {name: '陕西',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '吉林',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '福建',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '贵州',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '广东',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '青海',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '西藏',value: "",itemStyle:{color:'transparent'}},
                {name: '四川',value: "",itemStyle:{color:'transparent'}},
                {name: '宁夏',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '海南',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}},
                {name: '台湾',value: 0,itemStyle:{color:'transparent'}},
                {name: '香港',value: 0,itemStyle:{color:'transparent'}},
                {name: '澳门',value: Math.round(Math.random()*1000),itemStyle:{color:'transparent'}}
            ]
        }
    ]
  };