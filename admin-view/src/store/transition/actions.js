import * as types from './mutation-types'

export default {
  assignMessageText(context,text) {
    context.commit(types.ASSIGN_MESSAGE_TEXT,text);
  }
};