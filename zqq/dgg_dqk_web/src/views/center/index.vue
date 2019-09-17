<template>
  <div >
        <centerHeader></centerHeader>
        <div style='height:60px'></div>
        <transition name="slide-fade">
             <router-view/>
        </transition>
  </div>
</template>
<script>
import centerHeader from '@/components/centerHead'

export default {
    components: {centerHeader},
    data(){
        return{
            
        }
    },
    created(){
    },
    methods:{
    }
}
</script>

<style lang="stylus" scoped>

</style>