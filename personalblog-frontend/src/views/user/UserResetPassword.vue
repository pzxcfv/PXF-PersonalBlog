<template>
  <el-form
      ref="form"
      :model="ruleForm"
      status-icon
      :rules="rules"
      label-width="80px"
      class="demo-ruleForm"
      methods="patch"
  >
    <el-form-item label="原密码" prop="oldPassword">
      <el-input v-model="ruleForm.oldPWD" type="password" autocomplete="off"/>
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword">
      <el-input v-model="ruleForm.newPWD" type="password" autocomplete="off"/>
    </el-form-item>
    <el-form-item label="确认密码" prop="rePassword">
      <el-input
          v-model="ruleForm.rePWD"
          type="password"
          autocomplete="off"
      />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="userUpdatePassword()"
      >提交
      </el-button
      >
      <el-button @click="resetForm(ruleFormRef)">重置</el-button>
    </el-form-item>
  </el-form>
</template>
<!--;submitForm(ruleFormRef)-->

<!--ruleFormRef-->
<!--ruleForm-->
<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormInstance, FormRules} from 'element-plus'
import {userPasswordUpdateService} from '@/api/user'
import {ElMessage} from 'element-plus'


const userUpdatePassword = async () => {
  //调用接口
  let result = await userPasswordUpdateService(ruleForm.value);
  ElMessage.success(result.data.message ? result.data.message : '提交成功')
}
const ruleFormRef = ref<FormInstance>()

const checkNewPassword = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('请输入原密码'))
  }
  // setTimeout(() => {
  //   if (!Number.isInteger(value)) {
  //     callback(new Error('请输入数字'))
  //   } else {
  //       callback()
  //   }
  // }, 1000)  ;;submitForm(ruleFormRef)
}

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入新密码'))
  } else {
    if (ruleForm.value.rePWD !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('rePWD', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入新密码'))
  } else if (value !== ruleForm.value.newPWD) {
    callback(new Error("两次密码输入不一样!"))
  } else {
    callback()
  }
}

const ruleForm = ref({
  oldPWD: '',
  newPWD: '',
  rePWD: '',
})

const rules = reactive<FormRules<typeof ruleForm>>({
  newPWD: [{validator: validatePass, trigger: 'blur'}, {
    min: 5,
    max: 16,
    message: '长度为5~16位非空字符',
    trigger: 'blur'
  }],
  rePWD: [{validator: validatePass2, trigger: 'blur'}, {
    min: 5,
    max: 16,
    message: '长度为5~16位非空字符',
    trigger: 'blur'
  }],
  oldPWD: [{validator: checkNewPassword, trigger: 'blur'}, {
    min: 5,
    max: 16,
    message: '长度为5~16位非空字符',
    trigger: 'blur'
  }],
})

// const submitForm = (formEl: FormInstance | undefined) => {
//   if (!formEl) return
//   formEl.validate((valid) => {
//     if (valid) {
//       ElMessage.success("提交成功")
//     } else {
//       ElMessage.error('提交失败')
//       return false
//     }
//   })
// }

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
