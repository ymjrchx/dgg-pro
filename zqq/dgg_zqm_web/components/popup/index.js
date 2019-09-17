import Vue from 'vue'
import popup from './popup'
import login from './login'

let Pop = Vue.extend(popup)
let Login = Vue.extend(login)

const created = function (options) {
	var component = new Pop({
		el: document.createElement('div'),
    	data () {
    		return options
    	}
	})
	document.body.appendChild(component.$el)
	return component
}

const loginPop = function (options) {
	var component = new Login({
		el: document.createElement('div'),
    	data () {
    		return options
    	}
	})
	document.body.appendChild(component.$el)
	return component
}
export default {
	created,
	loginPop
}
