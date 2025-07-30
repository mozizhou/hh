<template>
  <div class="app-container home">
    <el-col>
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="搜索" prop="search">
              <el-input v-model="queryParams.search" placeholder="请输入查询内容" clearable
                        @keyup.enter.native="handleQuery" @input="handleQuery"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-col>
    <el-col :xs="24" :sm="24" :md="24" :lg="24">
      <el-card class="update-log">
        <div slot="header" class="clearfix">
          <el-button v-hasPermi="['dashboard:ctrl:all']" style="float: right; padding: 3px 0" type="text"
                     @click="handleAdd">新增
          </el-button>
        </div>
        <el-collapse accordion>
          <el-collapse-item v-for="home in homeList">
            <template slot="title">
              {{ home.title }}
              <el-button v-hasPermi="['dashboard:ctrl:all']" style="float: right;padding-left: 20px;" type="text"
                         @click="handleEdit(home)" icon="el-icon-edit"></el-button>
              <el-button v-hasPermi="['dashboard:ctrl:all']" style="float: right;padding-left: 5px;" type="text"
                         @click="handleDelete(home)" icon="el-icon-delete"></el-button>
            </template>
            <div class="flex-box">
              <div>
                {{ home.introduction }}
              </div>
              <div>链接地址：
                <el-link target="_blank" :href="home.link" type="primary">地址链接
                </el-link>
              </div>
              <div class="flex-justify-end">
                <el-image
                  style="width: 100px; height: 100px"
                  :src="home.picture"
                  :preview-src-list="srcList">
                </el-image>
              </div>
            </div>
            <el-divider content-position="left" style="color: grey">{{ home.origin }}</el-divider>
          </el-collapse-item>
        </el-collapse>
      </el-card>
    </el-col>
    <!--      <el-col :xs="24" :sm="24" :md="12" :lg="12" class="add">-->
    <!--        <el-button :xs="24" :sm="24" :md="12" :lg="12" icon="el-icon-plus"-->
    <!--                   style="width: 100%;height: 300px;"></el-button>-->
    <!--      </el-col>-->
    <el-dialog :title="'通知消息'" :visible.sync="open" width="30%" append-to-body onclose="onClickClose()" v-dialogDrag
               :close-on-click-modal="false">
      <el-form ref="form" :model="form" label-width="90px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入标题"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="简介" prop="introduction">
              <el-input v-model="form.introduction" placeholder="请输入简介"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="链接地址" prop="link">
              <el-input v-model="form.link" placeholder="请输入链接地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="信息来源" prop="origin">
              <el-input v-model="form.origin" placeholder="请输入信息来源"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="图片" prop="picture">
              <el-button size="mini" circle type="primary" icon="el-icon-upload" @click="handleUpload"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="'上传文件'" :visible.sync="openUpload" width="fit-content" append-to-body v-dialogDrag :close-on-click-modal="false">
      <el-upload drag class="upload-demo" ref="uploadFile" action="/stage-api/startproject/profile/avatarlist"
                 :on-change="handleFileChange"
                 :on-preview="handlePreview"
                 :on-remove="handleRemove"
                 :file-list="fileList"
                 :auto-upload="false">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
      <el-button style="width: 100%; margin-top: 15px" type="primary" icon="el-icon-upload" @click="submitUpload()">
        上传
      </el-button>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";

const baseURL = process.env.VUE_APP_BASE_API

export default {
  name: "Dashboard",
  data() {
    return {
      fileList: [],
      openUpload: false,
      srcList: [],
      homeList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        search: undefined
      },
      open: false,
      form: {},
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    onClickClose() {
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        title: undefined,
        introduction: undefined,
        link: undefined,
        origin: undefined,
        picture: undefined
      }
      this.resetForm("form")
    },
    getList() {
      this.queryParams.title = this.queryParams.search
      this.queryParams.introduction = this.queryParams.search
      this.queryParams.origin = this.queryParams.search
      getHomeList(this.addDateRange(this.queryParams)).then(res => {
        res.rows.map(data => {
          data.picture = baseURL + data.picture
        })
        this.homeList = res.rows
      })
    },
    handleAdd() {
      this.reset()
      this.open = true
    },
    handleEdit(row) {
      this.reset()
      this.open = true
      getHomeById(row.id).then(res => {
        this.form = res.data
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined && this.form.id != "") {
            editHome(this.form).then(res => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addHome(this.form).then(res => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return removeHomeList(id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    handleUpload(row, index) {
      this.openUpload = true;
    },
    handleRemove(file, fileList) {
    },
    handlePreview(file) {
    },
    handleFileChange(file, fileList) {
      this.fileList = fileList.map(file => {
        return file.raw
      });
    },
    submitUpload() {
      let formData = new FormData();
      for (let i = 0; i < this.fileList.length; i++) {
        formData.append("files", this.fileList[i]);
      }
      uploadFile(formData).then(response => {
        this.form.picture = response.urls
        this.openUpload = false
      })
    },
    goTarget(href) {
      window.open(href, "_blank")
    }
  }
}

export function getHomeList(data) {
  return request({
    url: '/system/homepagepolicy/list',
    method: 'post',
    data: data
  })
}

export function addHome(data) {
  return request({
    url: '/system/homepagepolicy',
    method: 'post',
    data: data
  })
}

export function editHome(data) {
  return request({
    url: '/system/homepagepolicy',
    method: 'put',
    data: data
  })
}

export function getHomeById(id) {
  return request({
    url: '/system/homepagepolicy/' + parseStrEmpty(id),
    method: 'get'
  })
}


export function removeHomeList(id) {
  return request({
    url: '/system/homepagepolicy/' + parseStrEmpty(id),
    method: 'delete'
  })
}

export function uploadFile(data) {
  return request({
    url: '/startproject/profile/avatarlist',
    headers: {'Content-Type': 'multipart/form-data'},
    method: 'post',
    data: data
  })
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }

  .add {
    .el-button:hover {
      color: #1890ff;
      border-color: #badeff;
      border: 1px dashed;
    }

    .el-button:focus {
      color: #1890ff;
      border-color: #badeff;
      border: 1px solid;
    }

    .el-icon-plus:before {
      color: white;
    }

    .el-icon-plus:hover {
      color: blue;
    }

    .el-button {
      border: white;
    }
  }
}
</style>

