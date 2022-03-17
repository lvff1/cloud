<template>
	<div class="school">
		<h2>学校名称：{{name}}</h2>
		<h2>学校地址：{{address}}</h2>
	</div>
</template>

<script>
	import pubsub from 'pubsub-js'
	export default {
		name:'School',
		props:['getSchoolName'],
		data() {
			return {
				name:'尚硅谷',
				address:'北京',
			}
		},
		mounted() {
			// this.$bus.$on('hello',data =>{
			// 	console.log("我是school组件，我收到了来自兄弟的数据："+data)
			// })
			this.pubId = pubsub.subscribe('hello',(msgName,msgData)=>{
				console.log("有人发布了"+msgData)
			})
		},
		beforeDestroy() {
			//取消订阅
			pubsub.unsubscribe(this.pubId)
			// this.$bus.$off("hello")
		},
	}
</script>

<style scoped>
	.school{
		background-color: skyblue;
		padding: 5px;
	}
</style>