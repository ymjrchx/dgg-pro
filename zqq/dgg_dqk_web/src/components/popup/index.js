import Vue from 'vue'
import popup from './popup'

let Pop = Vue.extend(popup)
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

export default {
	created
}
