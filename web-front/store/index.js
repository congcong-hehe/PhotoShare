export const state = () => ({
  isLogin: false,
  user: ''
})

export const mutations = {
  online (state) {
    state.isLogin = true;
  },
  offline(state) {
    state.isLogin = false;
  },
  login (state, name) {
    state.user = name;
  }
}