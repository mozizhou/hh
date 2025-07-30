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
            <el-form-item label="试用日期" prop="dateRange">
              <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd"
                              type="daterange" range-separator="-" start-placeholder="开始日期"
                              end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="试用单位" prop="trialCompany">
              <el-input v-model="queryParams.trialCompany" placeholder="请输入内容"/>
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

    <el-table v-loading="loading" :data="trailList">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="公司" align="center" prop="company"/>
      <el-table-column label="项目名称" align="center" prop="projectName"></el-table-column>
      <el-table-column label="试用时间" align="center" prop="trialDate"></el-table-column>
      <el-table-column label="试用单位" align="center" prop="trialCompany"/>
      <el-table-column label="进程" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">{{ getStatusName(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
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
            v-if="scope.row.status != 30002 && scope.row.status != 30003"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >修改
          </el-button>
          <el-button
            v-if="scope.row.status == 30002 || scope.row.status == 30004"
            size="mini"
            type="text"
            icon="el-icon-chat-dot-round"
            @click="handleFeed(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >反馈
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

    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body onclose="onClickClose()" v-dialogDrag :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :disabled="disable">
        <el-row>
          <el-col :span="6">
            <el-form-item label="项目名称" prop="projectId">
              <el-cascader
                filterable style="width: 100%"
                v-model="form.projectId"
                :options="deptOptions"
                :props="{ expandTrigger: 'hover' }"
                @change="handleChange"></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请输入产品名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="试用时间" prop="trialDate">
              <el-date-picker v-model="form.trialDate" value-format="yyyy/MM/dd"
                              type="date"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="产品功能" prop="productFeatures">
              <el-input type="textarea" :rows="5" v-model="form.productFeatures" placeholder="请输入产品功能"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="试用报告" prop="file">
              <el-row v-for="file in form.file">
                <el-link type="primary" style=" margin-right: 20px" @click="downLoad(file)">{{file.substr(file.lastIndexOf('/') + 1, file.length)}}</el-link>
              </el-row>
              <el-button size="mini" circle type="primary" icon="el-icon-upload" @click="handleUpload"/>
            </el-form-item>
          </el-col>
          <el-col v-for="(test, index) in form.testFeedList" :key="test">
            <el-table :data="test.testList" border :key="tableKey">
              <el-table-column :label="'第' + (index + 1) + '次反馈'" align="center">
                <el-table-column label="日期" align="center" key="date" prop="date">
                  <template slot-scope="scope">
                    <el-date-picker v-model="test.testList[scope.$index].feedDate" value-format="yyyy/MM/dd"
                                    type="date" :disabled="(!(form.testFeedList.length === (index + 1)) || form.status == 30001)"></el-date-picker>
                  </template>
                </el-table-column>
                <el-table-column label="产品性能" align="center" key="productPerformance"
                                 prop="productPerformance"
                                 :show-overflow-tooltip="true">
                  <template slot-scope="scope">
                    <el-input type="textarea" :rows="5" v-model="test.testList[scope.$index].productPerformance"
                              placeholder="请输入产品性能" :disabled="!(form.testFeedList.length === (index + 1)) || form.status == 30001"/>
                  </template>
                </el-table-column>
                <el-table-column label="改进事项" align="center" key="improvements" prop="improvements"
                                 :show-overflow-tooltip="true">
                  <template slot-scope="scope">
                    <el-input type="textarea" :rows="5" v-model="test.testList[scope.$index].improvements"
                              placeholder="请输入改进事项" :disabled="!(form.testFeedList.length === (index + 1)) || form.status == 30001"/>
                  </template>
                </el-table-column>
                <el-table-column label="解决方案" align="center" key="solution" prop="solution"
                                 :show-overflow-tooltip="true">
                  <template slot-scope="scope">
                    <el-input type="textarea" :rows="5" v-model="test.testList[scope.$index].solution"
                              placeholder="请输入改进事项" :disabled="!(form.testFeedList.length === (index + 1)) || form.status == 30001"/>
                  </template>
                </el-table-column>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-delete"
                      @click="handleDeleteTest(scope.row, scope.$index)"
                      v-hasPermi="['system:notice:remove']"
                    >删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table-column>
            </el-table>
            <el-row v-if="(form.testFeedList.length === (index + 1)) && form.status == 30002">
              <el-col :span="24" style="margin-bottom: 20px">
                <el-button class="mt-4" style="width: 100%" @click="onAddItem">
                  添加行
                </el-button>
              </el-col>
            </el-row>
          </el-col>
          <!--          <el-col v-if="form.id && form.status >= 30004" :span="24">-->
          <!--            <el-form-item label="中试产品性能" prop="productPerformance">-->
          <!--              <el-input type="textarea" :rows="5" v-model="form.middleProductPerformance" placeholder="请输入产品性能"/>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <!--          <el-col v-if="form.id && form.status >= 30004" :span="24">-->
          <!--            <el-form-item label="中试需要改进事项">-->
          <!--              <el-input type="textarea" :rows="5" v-model="form.middleImprovements" placeholder="请输入需要改进事项"/>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <el-col :span="24">
            <el-form-item label="试用单位">
              <el-input v-model="form.trialCompany" placeholder="请输入试用单位"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="!disable && form.status == 30002" type="success" @click="handleFinish">完 成</el-button>
        <el-button v-if="!disable && form.status == 30002" type="success" @click="handleStartTrial">开始试用</el-button>
        <el-button v-if="!disable && (form.status == 30001)" type="success"
                   @click="handleEndTrial">试用结束
        </el-button>
        <el-button v-if="!disable" type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="'上传文件'" :visible.sync="openUpload" width="fit-content" append-to-body v-dialogDrag :close-on-click-modal="false">
      <el-upload drag class="upload-demo" ref="uploadFile" action="/stage-api/startproject/profile/avatarlist"
                 :on-change="handleFileChange"
                 :on-preview="handlePreview"
                 :on-remove="handleRemove"
                 :file-list="fileList" multiple="multiple"
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
import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";

export default {
  name: "Notice",
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      fileList: [],
      // 是否显示上传弹出层
      openUpload: false,
      tableKey: 0,
      // 日期范围
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
      trailList: [],
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
          label: "试用中",
          value: 30001
        },
        {
          label: "用户反馈",
          value: 30002
        },
        {
          label: "完成",
          value: 30003
        }
      ],
      smailTestList: [],
      // 表单参数
      form: {
        file: []
      },
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
    downLoad(file) {
      this.$download.resource(file,false)
    },
    submitUpload() {
      let formData = new FormData();
      for (let i = 0; i < this.fileList.length; i++) {
        formData.append("files", this.fileList[i]);
      }
      uploadFile(formData).then(response => {
        // 确保 this.form.file 是数组
        if (!Array.isArray(this.form.file)) {
          this.form.file = [];
        }
        // 检查 response.urls 是否存在
        if (response.urls) {
          if (Array.isArray(response.urls)) {
            // 如果 response.urls 是数组，使用扩展运算符添加到 this.form.file
            this.form.file.push(...response.urls);
          } else {
            // 如果 response.urls 是单个值，直接 push
            this.form.file.push(response.urls);
          }
        }
        this.openUpload = false
        this.tableKey++
      })
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleFileChange(file, fileList) {
      this.fileList = fileList.map(file => {
        return file.raw
      });
    },
    handleUpload(row, index) {
      this.tableDateIndex = index
      this.openUpload = true;
    },
    handleFinish() {
      this.form.status = 30003
      this.submitForm()
      this.open = false
    },
    handleChange() {
      this.form.projectName = this.options.find(option => option.value == this.form.projectId[1]).label
    },
    onAddItem() {
      this.form.testFeedList.findLast(() => true).testList.push({
        index: this.form.testFeedList.length.toString(),
        date: '',
        smailProductPerformance: '',
        smailImprovements: ''
      })
    },
    handleEndTrial() {
      this.form.status = 30002
      this.form.testFeedList.push({
        index: this.form.testFeedList.length + 1,
        testList: [{index: this.form.testFeedList.length + 1}]
      })
      this.submitForm()
      this.open = false
    },
    handleStartTrial() {
      this.form.status = 30001
      this.submitForm()
      this.open = false
    },
    getStatusType(status) {
      let type
      if (status == 30001) {
        type = "warning"
      } else if (status == 30002) {
        type = ""
      } else if (status == 30003) {
        type = "success"
      }
      return type
    },
    /** 获取状态名称 */
    getStatusName(statusNo) {
      let statusName = '';
      this.statusList.forEach(status => {
        if (status.value == statusNo) {
          statusName = status.label
        }
      })
      if (statusName === '') {
        statusName = '持续跟进'
      }
      return statusName
    },
    submit() {
      this.trailList.forEach(trial => {
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
      getTrialList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.trailList = response.rows
        this.total = response.total
        this.loading = false
      })
      this.loading = false
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
        status: undefined,
        file: []
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
      if (this.form['trialDate']) {
        this.form['trialDate'] = ''
      }
      if (this.form['trialCompany']) {
        this.form['trialCompany'] = ''
      }
      if (this.form['id']) {
        this.form['id'] = ''
      }
      this.form.projectId = [this.form.company, Number(this.form.projectId)]
      this.disable = false
      this.open = true
      this.title = "添加产试用记录"
      this.fileList = []
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTrialById({id}).then(response => {
        response.data.projectId = [response.data.company, Number(response.data.projectId)]
        this.form = response.data
        if (!this.form.testFeedList) {
          this.form.testFeedList = []
        }
        if (this.form.file) {
          this.form.file = this.form.file.split(",");
        }
        this.open = true
        this.title = "修改产品试用记录"
        this.disable = false
      })
    },
    handleFeed(row) {
      this.reset()
      const id = row.id || this.ids
      getTrialById({id}).then(response => {
        response.data.projectId = [response.data.company, Number(response.data.projectId)]
        this.form = response.data
        if (!this.form.testFeedList) {
          this.form.testFeedList = []
        }
        if (this.form.file) {
          this.form.file = this.form.file.split(",");
        }

        this.open = true
        this.title = "产品试用反馈记录"
        this.disable = false
      })
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset()
      const id = row.id || this.ids
      getTrialById({id}).then(response => {
        response.data.projectId = [response.data.company, Number(response.data.projectId)]
        this.form = response.data
        if (!this.form.testFeedList) {
          this.form.testFeedList = []
        }
        if (this.form.file) {
          this.form.file = this.form.file.split(",");
        }
        this.disable = true
        this.open = true
        this.title = "查看产品试用记录"
      })
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined && this.form.id != "") {
            this.form.company = this.form.projectId[0]
            this.form.projectId = this.form.projectId[1]
            let fileList = ''
            if (this.form.file) {
              this.form.file.forEach((file, index) => {
                if (this.form.file.length == index + 1) {
                  fileList = fileList + file
                } else {
                  fileList = fileList + file + ','
                }
              })
            }
            this.form.file = fileList
            updateTrialList(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            this.form.status = 30001
            this.form.company = this.form.projectId[0]
            this.form.projectId = this.form.projectId[1]
            let fileList = ''
            if (this.form.file) {
              this.form.file.forEach((file, index) => {
                if (this.form.length == index + 1) {
                  fileList = fileList + file
                } else {
                  fileList = fileList + file + ','
                }
              })
            }
            this.form.file = fileList
            insertTrialList(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDeleteTest(row, index) {
      const id = row.id
      const formId = this.form.id
      if (id) {
        this.$modal.confirm('是否确认删除反馈记录编号为"' + id + '"的数据项？').then(function () {
          return removeFeedbackList(id)
        }).then(() => {
          getTrialById({id: formId}).then(response => {
            response.data.projectId = [response.data.company, Number(response.data.projectId)]
            this.form = response.data
          })
          this.$modal.msgSuccess("删除成功")
        }).catch(() => {
        })
      } else {
        this.form.testFeedList[row.index - 1].testList.splice(index, 1)
      }

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids
      this.$modal.confirm('是否确认删除试用记录编号为"' + id + '"的数据项？').then(function () {
        return removeTrialList(id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    }
  }
}

export function getTrialList(data) {
  return request({
    url: '/system/main/list',
    method: 'get',
    params: data
  })
}

export function insertTrialList(data) {
  return request({
    url: '/system/main',
    method: 'post',
    data: data
  })
}

export function updateTrialList(data) {
  return request({
    url: '/system/main',
    method: 'put',
    data: data
  })
}

export function removeTrialList(id) {
  return request({
    url: '/system/main/' + parseStrEmpty(id),
    method: 'delete'
  })
}

export function removeFeedbackList(id) {
  return request({
    url: '/system/feedback/' + parseStrEmpty(id),
    method: 'delete'
  })
}

export function getCompanyList() {
  return request({
    url: '/startproject/profile/list',
    method: 'get'
  })
}

export function getTrialById(data) {
  return request({
    url: '/system/main/listBymainid',
    method: 'get',
    params: data
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
