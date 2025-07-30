<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-row :gutter="10" style="margin-bottom: 15px" type="flex">
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="公司" prop="company">
              <el-input
                v-model="queryParams.company"
                placeholder="请输入公司名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="项目名称" prop="projectName">
              <el-input
                v-model="queryParams.projectName"
                placeholder="请输入项目名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="项目编号" prop="projectNo">
              <el-input
                v-model="queryParams.projectNo"
                placeholder="请输入项目编号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="负责人" prop="header">
              <el-input v-model="queryParams.header" placeholder="请输入负责人"/>
            </el-form-item>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10" type="flex" class="mb8" style="margin-bottom: 15px">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:notice:add']"
          >新增
          </el-button>
        </el-col>
        <el-col style="display: flex;justify-content: flex-end;">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-col>
      </el-row>
    </el-form>

    <el-row style="margin-bottom: 0px">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" prop="id"/>
      <el-table-column label="公司" align="center" prop="company"/>
      <el-table-column label="项目名称" align="center" prop="projectName"></el-table-column>
      <el-table-column label="项目编号" align="center" prop="projectNo"></el-table-column>
      <el-table-column label="负责人" align="center" prop="header"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >查看
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:notice:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body onclose="onClickClose()" :key="tableKey" v-dialogDrag :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" :disabled="disable">
        <el-row>
          <el-col :span="7">
            <el-form-item label="项目编号" prop="projectNo">
              <el-input v-model="form.projectNo" placeholder="请输入项目编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="项目名称" prop="projectId">
              <el-cascader
                filterable style="width: 100%"
                v-model="form.projectId"
                :options="deptOptions"
                :props="{ expandTrigger: 'hover' }"
                @change="handleChange"></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="负责人" prop="header">
              <el-input v-model="form.header" placeholder="请输入负责人名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="对产品功能、性能的审核意见" prop="reviewOpinions">
              <el-input type="textarea" :rows="5" v-model="form.reviewOpinions" placeholder="请输入产品性能"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="产品成本的符合性" prop="costCompliance">
              <el-input type="textarea" :rows="5" v-model="form.costCompliance" placeholder="请输入需要改进事项"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="批量试产的可行性" prop="viability">
              <el-input type="textarea" :rows="5" v-model="form.viability" placeholder="请输入需要改进事项"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="评审结论" prop="conclusions">
              <el-input v-model="form.conclusions" placeholder="请输入评审结论"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="附件" prop="files">
              <el-link v-if="form.files && form.files !== ''" type="primary" style=" margin-right: 20px" @click="downLoad(form.files)">文件链接</el-link>
              <el-button-group>
                <el-button size="mini" circle type="primary" icon="el-icon-upload" @click="handleUpload"/>
                <el-button size="mini" type="success" circle icon="el-icon-link"/>
              </el-button-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item label="项目负责人意见" prop="headerRecommendations" label-width="100px">
              <el-input type="textarea" :rows="4" v-model="form.headerRecommendations"
                        placeholder="请输入产品的项目负责人意见"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目负责人" prop="projectHeader" label-width="100px">
              <el-input v-model="form.projectHeader" placeholder="请输入产品的项目负责人" maxlength="30"/>
            </el-form-item>
            <el-form-item label="日期" :prop="projectHeaderDate" label-width="100px">
              <el-date-picker v-model="form.projectHeaderDate" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item label="总经理意见" prop="managerRecommendations" label-width="100px">
              <el-input type="textarea" :rows="4" v-model="form.managerRecommendations"
                        placeholder="请输入产品的总经理意见"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="总经理" prop="manager" label-width="100px">
              <el-input v-model="form.manager" placeholder="请输入产品的总经理" maxlength="30"/>
            </el-form-item>
            <el-form-item label="日期" prop="managerDate" label-width="100px">
              <el-date-picker v-model="form.managerDate" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onSubmit">导 出</el-button>
        <el-button v-if="!disable" type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 上传文件对话框 -->
    <el-dialog :title="'上传文件'" :visible.sync="openUpload" width="fit-content" append-to-body>
      <el-upload
        drag
        class="upload-demo"
        ref="uploadFile"
        action="/stage-api/startproject/profile/avatar"
        :on-change="handleUploadChange"
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

<style>
.el-date-editor.el-input {
  width: 100% !important;
}

.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.flex-text-col-status {
  label {
    white-space: nowrap;
  }
}

.flex-text-col {
  div {
    display: flex !important;
  }

  .el-date-editor--daterange.el-input__inner {
    width: auto;
  }

  label {
    white-space: nowrap;
  }
}
</style>

<script>
import {listNotice, getNotice, delNotice, addNotice, updateNotice} from "@/api/system/notice"
import store from "@/store";
import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";
import JSZipUtils from "jszip-utils";
import JSZip from "pizzip";
import Docxtemplater from "docxtemplater";

export default {
  name: "Notice",
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      fileList: [],
      tableKey: 0,
      // 是否显示上传弹出层
      openUpload: false,
      dateRange: [],
      deptOptions: [],
      options: [],
      disable: false,
      trackingIndex: 0,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      reportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层2
      open2: false,
      // 是否显示弹出终审报告层
      finalReviewOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        company: "",
        projectName: "",
        trialDate: "",
        trialCompany: "",
      },
      statusList: [
        {
          label: "已完成",
          value: "10001",
        },
        {
          label: "持续跟进",
          value: "10002"
        },
        {
          label: "未完成",
          value: "10003"
        },
        {
          label: "延后完成",
          value: "10004"
        },
        {
          label: "准备试制",
          value: 20001
        },
        {
          label: "试制中",
          value: 20002
        },
        {
          label: "终审中",
          value: 20003
        },
        {
          label: "终审完成",
          value: 20004
        },
        {
          label: "取消",
          value: 20005
        }
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        noticeTitle: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        noticeType: [
          {required: true, message: "类型不能为空", trigger: "change"}
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    onSubmit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.exportWord();
        } else {
          return false;
        }
      });
    },
    exportWord() {
      const _this = this
      JSZipUtils.getBinaryContent('/static/结题报告.docx', function(error, content) {
        // 抛出异常
        if (error) {
          throw error
        }
        // 创建一个JSZip实例，内容为模板的内容
        let zip = new JSZip(content)
        // 创建并加载docxtemplater实例对象
        let doc = new Docxtemplater().loadZip(zip)
        // 设置模板变量的值
        // doc.setData(
        //   _this.form,
        //   _this.table
        // )
        _this.form = _this.convertUndefinedToEmpty(_this.form)
        console.log(_this.form)
        doc.setData({
          ..._this.form
        })
        try {
          // 用模板变量的值替换所有模板变量
          doc.render()
        } catch (error) {
          // 抛出异常
          // let e = {
          //   message: error.message,
          //   name: error.name,
          //   stack: error.stack,
          //   properties: error.properties,
          // }
          this.$message.error('导出报表失败')
          throw error
        }

        // 生成一个代表docxtemplater对象的zip文件（不是一个真实的文件，而是在内存中的表示）
        let out = doc.getZip().generate({
          type: 'blob',
          mimeType:
            'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
        })
        // 将目标文件对象保存为目标类型的文件，并命名
        saveAs(out, '结题报告.docx')
      })
    },
    convertUndefinedToEmpty(obj) {
      // 处理非对象类型
      if (obj === null || typeof obj !== 'object') {
        return obj;
      }

      // 处理数组
      if (Array.isArray(obj)) {
        return obj.map(item => this.convertUndefinedToEmpty(item));
      }

      // 处理普通对象
      const result = {};
      for (const [key, value] of Object.entries(obj)) {
        result[key] = value === undefined ? '' : this.convertUndefinedToEmpty(value);
        result[key] = value === null ? '' : this.convertUndefinedToEmpty(value);
      }
      return result;
    },
    downLoad(file) {
      this.$download.resource(file,false)
    },
    handleRemove(file, fileList) {
    },
    handlePreview(file) {
    },
    handleUploadChange(file, fileList) {
      this.fileList[0] = file.raw;
    },
    handleUpload() {
      this.openUpload = true;
    },
    submitUpload() {
      let formDate = new FormData();
      formDate.append("avatarfile", this.fileList[0]);
      uploadFile(formDate).then(response => {
        this.form.files = response.Url
        this.openUpload = false
        this.tableKey++
      })
    },
    handleChange() {
      this.form.projectName = this.options.find(option => option.value == this.form.projectId[1]).label
    },
    submit() {
      this.reportList.forEach(trial => {
        if (this.finalReviewForm.id == trial.id) {
          trial.status = 20004
          this.finalReviewOpen = false
        }
      })
    },
    onClickClose() {
      this.reset();
      this.disable = false
    },
    /** 查询列表 */
    getList() {
      this.loading = true
      getCompanyList().then(response => {
        response.data.map(data => {
          data.value = data.company
          data.label = data.company
          this.options = [...this.options, ...data.projectNames]
          data.projectNames.map(project => {
            project.value = project.id
            project.label = project.projectName
          })
          data.children = data.projectNames
        })

        this.deptOptions = response.data
      })
      getReportList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.reportList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open2 = false;
      this.open = false
      this.finalReviewOpen = false
      this.disable = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        projectName: undefined,
        quantity: undefined,
        equipment: undefined,
        prototypeStatus: undefined,
        trialProducer: undefined,
        date: undefined,
        status: undefined
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.disable = false
      this.open = true
      this.title = "添加产品结题报告"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id
      getReportById(id).then(response => {
        response.data.projectId = [response.data.company, Number(response.data.projectId)]
        this.form = response.data
        this.open = true
        this.title = "修改产品结题报告"
        this.disable = false
      })
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset()
      const id = row.id
      getReportById(id).then(response => {
        response.data.projectId = [response.data.company, Number(response.data.projectId)]
        this.form = response.data
        this.disable = true
        this.open = true
        this.title = "查看产品结题报告"
      })
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined && this.form.id != "") {
            this.form.company = this.form.projectId[0]
            this.form.projectId = this.form.projectId[1]
            updateReportList(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            this.form.company = this.form.projectId[0]
            this.form.projectId = this.form.projectId[1]
            insertReportList(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除结题报告编号为"' + id + '"的数据项？').then(function () {
        return removeReportList(id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },

  }
}

export function getReportList(data) {
  return request({
    url: '/system/report/list',
    method: 'get',
    params: data
  })
}

export function insertReportList(data) {
  return request({
    url: '/system/report',
    method: 'post',
    data: data
  })
}

export function updateReportList(data) {
  return request({
    url: '/system/report',
    method: 'put',
    data: data
  })
}

export function removeReportList(id) {
  return request({
    url: '/system/report/' + parseStrEmpty(id),
    method: 'delete'
  })
}

export function getCompanyList() {
  return request({
    url: '/startproject/profile/list',
    method: 'get'
  })
}

export function getReportById(id) {
  return request({
    url: '/system/report/' + parseStrEmpty(id),
    method: 'get'
  })
}

export function uploadFile(data) {
  return request({
    url: '/startproject/profile/avatar',
    headers: {'Content-Type': 'multipart/form-data'},
    method: 'post',
    data: data
  })
}

</script>
