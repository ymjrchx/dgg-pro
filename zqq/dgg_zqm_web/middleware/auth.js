export default function ({ store, redirect }) {
	if (!store.state.userInfo.userId) {
	  return redirect('/')
	 }
}