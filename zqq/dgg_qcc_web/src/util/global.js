import { Message,MessageBox} from 'element-ui'

const Global={
    alertMessage(msg="此数据只对VIP用户开放",title="温馨提示"){   //Vip消息
        MessageBox({
            title: title,
            message: msg,
            callback: action => {}
          });
    },
}

export default Global