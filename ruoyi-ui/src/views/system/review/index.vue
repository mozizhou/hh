<template>
  <div class="app-container">
    <el-row :gutter="20">
      <splitpanes :horizontal="this.$store.getters.device === 'mobile'" class="default-theme">
        <!--部门数据-->
        <pane size="16">
          <el-col>
            <div class="head-container">
              <el-input v-model="deptName" placeholder="请输入公司名称" clearable size="small"
                        prefix-icon="el-icon-search" style="margin-bottom: 20px"/>
            </div>
            <div class="head-container">
              <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false"
                       :filter-node-method="filterNode" ref="tree" node-key="id" default-expand-all highlight-current
                       @node-click="handleNodeClick"/>
            </div>
          </el-col>
        </pane>
        <!--用户数据-->
        <pane size="84">
          <el-col>
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
              <el-row :gutter="10" style="margin-bottom: 0" type="flex">
                <el-col :span="6">
                  <div class="grid-content flex-text-col">
                    <el-form-item label="项目编号" prop="projectNo">
                      <el-input v-model="queryParams.projectNo" placeholder="请输入用户名称" clearable
                                @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="grid-content flex-text-col">
                    <el-form-item label="项目名称" prop="projectName">
                      <el-input v-model="queryParams.projectName" placeholder="请输入用户名称" clearable
                                @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="grid-content flex-text-col">
                    <el-col>
                      <el-form-item label="创建时间">
                        <el-date-picker v-model="dateRange" value-format="yyyy-MM-dd" type="daterange"
                                        range-separator="-" start-placeholder="开始日期"
                                        end-placeholder="结束日期"></el-date-picker>
                      </el-form-item>
                    </el-col>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="grid-content flex-text-col">
                    <el-form-item label="负责人" prop="header">
                      <el-input v-model="queryParams.header" placeholder="请输入负责人" clearable
                                @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>

              <el-row :gutter="10" type="flex" class="mb8" style="margin-bottom: 15px">
                <el-col style="display: flex;justify-content: flex-end;">
                  <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                  <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                </el-col>
              </el-row>
            </el-form>


            <el-table v-loading="loading" :data="reviewList" @selection-change="handleSelectionChange"
                      highlight-current-row>
              <el-table-column type="selection" width="50" align="center"/>
              <el-table-column label="公司名称" align="center" key="userId" prop="company" v-if="columns[0].visible"/>
              <el-table-column label="项目编号" align="center" key="userName" prop="projectNo" v-if="columns[1].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="项目名称" align="center" key="nickName" prop="projectName"
                               v-if="columns[2].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="创建时间" align="center" prop="createDate" v-if="columns[6].visible">
                <template slot-scope="scope">
                  <span>{{ scope.row.createDate }}</span>
                </template>
              </el-table-column>
              <el-table-column label="负责人" align="center" key="header" prop="header" v-if="columns[7].visible"/>
              <el-table-column label="状态" align="center" key="status" prop="status" v-if="columns[8].visible">
                <template slot-scope="scope">
                  <el-tag :type="getStatusType(scope.row.status)">{{ getStatusName(scope.row.status) }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
                <template slot-scope="scope" v-if="scope.row.userId !== 1">
                  <el-button size="mini" type="text" icon="el-icon-view" @click="handleUpdate(scope.row, 1)"
                             v-hasPermi="['system:user:edit']">查看
                  </el-button>
                  <el-button v-if="scope.row.status==0" size="mini" type="text" icon="el-icon-check"
                             @click="handleUpdate(scope.row, 2)"
                             v-hasPermi="['system:user:edit']">审核
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                        :limit.sync="queryParams.pageSize" @pagination="getList"/>
          </el-col>
        </pane>
      </splitpanes>
    </el-row>
    <!-- 上传文件对话框 -->
    <el-dialog :title="'上传文件'" :visible.sync="openUpload" width="fit-content" append-to-body v-dialogDrag
               :close-on-click-modal="false">
      <el-upload
        drag
        class="upload-demo"
        ref="uploadFile"
        action="/stage-api/startproject/profile/avatar"
        :on-change="handleChange"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :file-list="fileList"
        :auto-upload="false">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip"></div>
      </el-upload>
      <el-button style="width: 100%; margin-top: 15px" type="primary" icon="el-icon-upload" @click="submitUpload()">
        上传
      </el-button>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :key="tableKey" v-dialogDrag
               :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :disabled="disabled">
        <div>
          <el-row>
            <el-col :span="8">
              <el-form-item label="项目号" :prop="form.projectNo" label-width="100px">
                <el-input v-model="form.projectNo" placeholder="请输入项目号" maxlength="30"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="项目名称" :prop="form.projectName" label-width="100px">
                <el-input v-model="form.projectName" placeholder="请输入项目名称" maxlength="30"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="负责人" :prop="form.header" label-width="100px">
                <el-input v-model="form.header" placeholder="请输入负责人昵称" maxlength="30"/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label="设计理念" :prop="form.synopsis" label-width="100px">
                <el-input
                  type="textarea"
                  :rows="2"
                  placeholder="请输入内容"
                  v-model="form.synopsis">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="成本单价" label-width="100px">
                <el-input
                  type="number"
                  placeholder="请输入成本单价"
                  v-model="form.price">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="货币/单位" :prop="form.unit" label-width="100px">
                <el-input
                  placeholder="请输入货币/单位"
                  v-model="form.unit">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="试产可行性" :prop="form.trialProduction" label-width="100px">
                <el-input
                  type="textarea"
                  :rows="2"
                  placeholder="请输入内容"
                  v-model="form.trialProduction">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="采购可行性" :prop="form.procurementProduction" label-width="100px">
                <el-input
                  type="textarea"
                  :rows="2"
                  v-model="form.procurementProduction"
                  placeholder="请输入内容"/>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="图纸审核意见" :prop="form.drawingReviewOpinions" label-width="100px">
                <el-input
                  type="textarea"
                  :rows="2"
                  v-model="form.drawingReviewOpinions"
                  placeholder="请输入内容"/>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="评审结论" :prop="form.conclusions" label-width="100px">
                <el-input
                  type="textarea"
                  :rows="2"
                  v-model="form.conclusions"
                  placeholder="请输入评审结论"/>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="附件" :prop="form.annex" label-width="100px">
                <el-link v-if="form.file && form.file !== ''" type="primary" style=" margin-right: 20px"
                         @click="downLoad(form.file)">文件链接
                </el-link>
                <el-button-group>
                  <el-button size="mini" circle type="primary" icon="el-icon-upload" @click="handleUpload"/>
                  <el-button size="mini" type="success" circle icon="el-icon-link"/>
                </el-button-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="负责人意见" :prop="form.herderOpinion" label-width="100px">
                <el-input
                  type="textarea"
                  :rows="2"
                  v-model="form.herderOpinion"
                  placeholder="请输入负责人意见"/>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="总经理意见" :prop="form.managerOption" label-width="100px">
                <el-input
                  type="textarea"
                  :rows="2"
                  v-model="form.managerOption"
                  placeholder="总经理意见"/>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onSubmit">导 出</el-button>
        <el-button v-if="!disabled" type="danger" @click="submitForm(1)">不通过</el-button>
        <el-button v-if="!disabled" type="success" @click="submitForm(2)">通 过</el-button>
        <el-button v-if="!disabled" type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
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
import {
  listUser,
  getUser,
  delUser,
  addUser,
  updateUser,
  resetUserPwd,
  changeUserStatus,
  deptTreeSelect
} from "@/api/system/user"
import {getToken} from "@/utils/auth"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import {Splitpanes, Pane} from "splitpanes"
import "splitpanes/dist/splitpanes.css"
import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";
import tab from "@/plugins/tab";
import JSZipUtils from "jszip-utils";
import JSZip from "pizzip";
import Docxtemplater from "docxtemplater";

export default {
  dateName: "dataList",
  name: "User",
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  components: {Treeselect, Splitpanes, Pane},
  data() {
    return {
      tableKey: 0,
      disabled: false,
      fileList: [],
      tableLabel: [
        {label: '序号', prop: 'id'},
        {label: '场所编号', prop: 'number'},
        {label: '表头3', prop: 'a'},
        {label: '表头4', prop: 'b'},
      ],
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
      reviewList: null,
      // 弹出层标题
      title: "",
      // 所有部门树选项
      deptOptions: undefined,
      // 过滤掉已禁用部门树选项
      enabledDeptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 是否显示上传弹出层
      openUpload: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      // 表单参数
      form: {
        company: '',
        conclusions: '',
        createBy: '',
        createDate: '',
        createTime: '',
        drawingReviewOpinions: '',
        file: '',
        header: '',
        herderOpinion: '',
        id: '',
        managerOption: '',
        price: '',
        procurementProduction: '',
        projectName: '',
        projectNo: '',
        projectid: '',
        remark: '',
        status: '',
        synopsis: '',
        trialProduction: '',
        unit: '',
        updateBy: '',
        updateTime: ''
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      statusList: [
        {
          label: "未审批",
          value: 0,
        },
        {
          label: "不通过",
          value: 1
        },
        {
          label: "通过",
          value: 2
        }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectid: undefined,
        company: undefined,
        projectNo: undefined,
        projectName: undefined,
        createDate: undefined,
        header: undefined
      },
      // 列信息
      columns: [
        {key: 0, label: `用户编号`, visible: true},
        {key: 1, label: `用户名称`, visible: true},
        {key: 2, label: `用户昵称`, visible: true},
        {key: 3, label: `部门`, visible: true},
        {key: 4, label: `手机号码`, visible: true},
        {key: 5, label: `状态`, visible: true},
        {key: 6, label: `创建时间`, visible: true},
        {key: 7, label: `负责人`, visible: true},
        {key: 8, label: `状态`, visible: true}
      ],
      // 表单校验
      rules: {
        userName: [
          {required: true, message: "用户名称不能为空", trigger: "blur"},
          {min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur'}
        ],
        nickName: [
          {required: true, message: "用户昵称不能为空", trigger: "blur"}
        ],
        password: [
          {required: true, message: "用户密码不能为空", trigger: "blur"},
          {min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur'},
          {pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur"}
        ],
        email: [
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    }
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.getList()
    this.getDeptTree()
    this.getConfigKey("sys.user.initPassword").then(response => {
      this.initPassword = response.msg
    })
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
      JSZipUtils.getBinaryContent('/static/设计流程.docx', function (error, content) {
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
        saveAs(out, '设计流程.docx')
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
      this.$download.resource(file, false)
    },
    /** 获取状态类别 */
    getStatusType(status) {
      let type
      if (status == 0) {
        type = "warning"
      } else if (status == 2) {
        type = "success"
      } else if (status == 1) {
        type = "danger"
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
    submitUpload() {
      let formDate = new FormData();
      formDate.append("avatarfile", this.fileList[0]);
      uploadFile(formDate).then(response => {
        this.form.file = response.Url
        this.openUpload = false
        this.tableKey++
      })
    },
    handleRemove(file, fileList) {
    },
    handlePreview(file) {
    },
    handleChange(file, fileList) {
      this.fileList[0] = file.raw;
    },
    handleUpload() {
      this.openUpload = true;
    },

    /** 查询用户列表 */
    getList() {
      this.loading = true
      reviewList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.reviewList = response.rows
          this.total = response.total
          this.loading = false
        }
      )
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      getCompanyList().then(response => {
        response.data.map((res, index) => {
          res.children = res.projectNames
          res.disabled = false
          res.label = res.company
          res.id = index
          res.children.map(((child, index) => {
            child.children = child.projectNames
            child.disabled = false
            child.label = child.projectName
          }))
        })
        this.deptOptions = response.data
        this.enabledDeptOptions = this.filterDisabledDept(JSON.parse(JSON.stringify(response.data)))
      })
    },
    // 过滤禁用的部门
    filterDisabledDept(deptList) {
      return deptList.filter(dept => {
        if (dept.disabled) {
          return false
        }
        if (dept.children && dept.children.length) {
          dept.children = this.filterDisabledDept(dept.children)
        }
        return true
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    // 节点单击事件
    handleNodeClick(data) {
      if (data.company) {
        this.queryParams.projectid = undefined
        this.queryParams.company = data.company
      } else {
        this.queryParams.company = undefined
        this.queryParams.projectid = data.id
      }
      this.handleQuery()
    },
    // 取消按钮
    cancel() {
      this.disabled = false
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        company: undefined,
        projectNo: undefined,
        projectName: undefined,
        header: undefined,
        synopsis: undefined,
        price: undefined,
        trialProduction: undefined,
        procurementProduction: undefined,
        drawingReviewOpinions: undefined,
        Conclusions: undefined,
        annex: undefined
      }
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
      this.queryParams.deptId = undefined
      this.$refs.tree.setCurrentKey(null)
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row)
          break
        case "handleAuthRole":
          this.handleAuthRole(row)
          break
        default:
          break
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "项目立项文件"
    },
    /** 修改按钮操作 */
    handleUpdate(row, type) {
      this.reset()
      const id = row.id
      getReview(id).then(response => {
        if (type == 1) {
          this.disabled = true
          this.title = "查看终审表"
        } else if (type == 2) {
          this.disabled = false
          this.title = "设计终审表"
        }
        this.form = response.data
        this.open = true
      })
    },
    /** 提交按钮 */
    submitForm(status) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.price == '') {
            this.form.price = 0
          }
          if (status === 1) {
            this.form.status = 1
            updateReview(this.form).then(response => {
              this.$modal.msgSuccess("操作成功")
              this.open = false
              this.getList()
            })
          } else if (status === 2) {
            this.form.status = 2
            updateReview(this.form).then(response => {
              this.$modal.msgSuccess("操作成功")
              this.open = false
              this.getList()
            })
          } else {
            updateReview(this.form).then(response => {
              this.$modal.msgSuccess("操作成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 上传按钮 */
    uploadFile: function () {
      updateUser(this.form).then(response => {
        this.$modal.msgSuccess("修改成功")
        this.open = false
        this.getList()
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids
      this.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(function () {
        return delUser(userIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/user/importTemplate', {}, `user_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true})
      this.getList()
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    }
  }
}


export function uploadFile(data) {
  return request({
    url: '/startproject/profile/avatar',
    headers: {'Content-Type': 'multipart/form-data'},
    method: 'post',
    data: data
  })
}

export function reviewList(data) {
  return request({
    url: '/system/review/list',
    method: 'get',
    params: data
  })
}

export function getReview(id) {
  return request({
    url: '/system/review/' + parseStrEmpty(id),
    method: 'get'
  })
}

export function updateReview(data) {
  return request({
    url: '/system/review',
    method: 'put',
    data: data
  })
}

export function getCompanyList() {
  return request({
    url: '/startproject/profile/list',
    method: 'get'
  })
}
</script>
