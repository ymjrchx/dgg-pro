<template>
    <dt>
        <span></span>
        <img v-if="str == '工商核名接口' " src="@/assets/images/Api/001.png" style="padding-top:8%">
        <img v-else-if="str == '商标查询接口' " src="@/assets/images/Api/231.png" style="padding-top:8%">
        <img v-else-if="str == '商标详情查询接口' " src="@/assets/images/Api/231.png" style="padding-top:8%">
        <img v-else-if="str == '专利查询接口' " src="@/assets/images/Api/514.png" style="padding-top:8%">
        <img v-else-if="str == '专利详情查询接口' " src="@/assets/images/Api/514.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/216.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/513.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/233.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/255.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/260.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/271.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/411.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/420.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/421.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/422.png" style="padding-top:8%">
        <img v-else-if="str == '接口' " src="@/assets/images/Api/510.png" style="padding-top:8%">
        <img v-else src="@/assets/images/Api/513.png" style="padding-top:8%">
    </dt>
</template>
<script>
export default {
    name: "",
    props: {
        str: ""
    },
    data() {
        return {};
    }
};
</script>
<style scoped>
</style>