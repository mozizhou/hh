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
              <el-row :gutter="10" style="margin-bottom: 15px" type="flex">
                <el-col :span="6">
                  <div class="grid-content flex-text-col">
                    <el-form-item label="项目编号" prop="projectNo">
                      <el-input v-model="queryParams.projectNo" placeholder="请输入项目编号" clearable
                                @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="grid-content flex-text-col">
                    <el-form-item label="项目名称" prop="projectName">
                      <el-input v-model="queryParams.projectName" placeholder="请输入项目名称" clearable
                                @keyup.enter.native="handleQuery"/>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="grid-content flex-text-col">
                    <el-form-item label="创建时间">
                      <el-date-picker v-model="dateRange" value-format="yyyy-MM-dd" type="daterange"
                                      range-separator="-" start-placeholder="开始日期"
                                      end-placeholder="结束日期"></el-date-picker>
                    </el-form-item>
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
                <el-col :span="1.5">
                  <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                             v-hasPermi="['system:user:add']">新增
                  </el-button>
                </el-col>
                <el-col style="display: flex;justify-content: flex-end;">
                  <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                  <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                </el-col>
              </el-row>
            </el-form>


            <el-row style="margin-bottom: 0px">
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
            </el-row>

            <el-table v-loading="loading" :data="projectList" @cell-dblclick="handleView" highlight-current-row>
              <el-table-column type="selection" width="50" align="center"/>
              <el-table-column label="公司名称" align="center" key="company" prop="company" v-if="columns[0].visible"/>
              <el-table-column label="项目编号" align="center" key="projectNo" prop="projectNo"
                               v-if="columns[1].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="项目名称" align="center" key="projectName" prop="projectName"
                               v-if="columns[2].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="创建时间" align="center" prop="createDate" v-if="columns[6].visible">
                <template slot-scope="scope">
                  <span>{{ scope.row.createDate }}</span>
                </template>
              </el-table-column>
              <el-table-column label="负责人" align="center" key="header" prop="header" v-if="columns[7].visible"/>
              <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
                <template slot-scope="scope" v-if="scope.row.userId !== 1">
                  <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                             v-hasPermi="['system:user:edit']">修改
                  </el-button>
                  <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                             v-hasPermi="['system:user:remove']">删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                        :limit.sync="queryParams.pageSize" @pagination="getList"/>
          </el-col>
        </pane>
      </splitpanes>

      <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body v-dialogDrag
                 :close-on-click-modal="false" onclose="onClickClose()">
        <el-row style="margin-bottom: 30px;">
          <el-col :offset="8" :span="8">
            <el-steps style="max-width: 600px" :active="active" align-center finish-status="success">
              <el-step title="Step 1"/>
              <el-step title="Step 2"/>
            </el-steps>
          </el-col>
        </el-row>

        <el-form ref="form" :model="form" :rules="rules" label-width="80px" :disabled="disabled">
          <div v-if="active===0">
            <el-row>
              <el-col :span="12">
                <el-form-item label="公司名称" label-width="100px">
                  <el-input v-model="form.company" placeholder="请输入公司昵称" maxlength="30"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="创建日期" label-width="100px">
                  <el-date-picker v-model="form.createDate" value-format="yyyy/MM/dd" type="date"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
          <div v-if="active===1">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目号" label-width="100px">
                  <el-input v-model="form.projectNo" placeholder="请输入用户昵称" maxlength="30"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目名称" label-width="100px">
                  <el-input v-model="form.projectName" placeholder="请输入用户昵称" maxlength="30"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="负责人" label-width="100px">
                  <el-input v-model="form.header" placeholder="请输入用户昵称" maxlength="30"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-table
                :data="form.tableData"
                :span-method="objectSpanMethod"
                style="width: auto; margin-left: 30px"
                fit :key="tableKey"
            >
              <el-table-column label="分项内容">
                <el-table-column
                    header-align=“center”
                    align=“center”
                    label="项目进度">
                  项目进度
                </el-table-column>
                <el-table-column
                    label="项目">
                  <template #default="scope">
                    <el-input v-model="form.tableData[scope.$index].projectName"></el-input>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column
                  label="执行人"
                  width="200">
                <template #default="scope">
                  <el-input type="textarea" :rows="2" v-model="form.tableData[scope.$index].executor"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                  label="计划开始月份">
                <template #default="scope">
                  <el-date-picker v-model="form.tableData[scope.$index].startDate" value-format="yyyy/MM"
                                  type="month"></el-date-picker>
                </template>
              </el-table-column>
              <el-table-column
                  label="计划完成月份">
                <template #default="scope">
                  <el-date-picker v-model="form.tableData[scope.$index].endDate" value-format="yyyy/MM"
                                  type="month"></el-date-picker>
                </template>
              </el-table-column>
              <el-table-column prop="file"
                               label="输出文件">
                <template #default="scope">
                  <el-link v-if="scope.row.file && scope.row.file !== ''" type="primary"
                           @click="downLoad(scope.row.file)">文件链接
                  </el-link>
                </template>
              </el-table-column>

              <el-table-column fixed="right" label="操作" min-width="120">
                <template #default="scope">
                  <el-button-group>
                    <el-button size="mini" circle type="primary" icon="el-icon-upload"
                               @click="handleUpload(scope.row, scope.$index)"/>
                    <el-button size="mini" type="success" circle icon="el-icon-link"/>
                    <el-button size="mini" circle type="danger" icon="el-icon-delete"
                               @click.prevent="deleteRow(scope.$index)"/>
                  </el-button-group>
                </template>
              </el-table-column>
            </el-table>
            <el-row>
              <el-col :span="24" style="margin-bottom: 20px; padding-left: 30px">
                <el-button class="mt-4" style="width: 100%" @click="onAddItem">
                  添加行
                </el-button>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="创新点" label-width="100px">
                  <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入内容"
                      v-model="form.innovation">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="市场定位" label-width="100px">
                  <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入内容"
                      v-model="form.marketPosition">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="11">
                <el-form-item label="预算金额" label-width="100px" prop="budgetAmountNo">
                  <el-input v-model="form.budgetAmountNo" placeholder="请输入预算金额" maxlength="50" type="number">
                    <el-select v-model="unit" slot="append" placeholder="请选择" @change="changeSelected">
                      <el-option label="万" value="1"></el-option>
                      <el-option label="百万" value="2"></el-option>
                    </el-select>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="13">
                <el-form-item label="预估年销售" label-width="100px" prop="annualSalesNo">
                  <el-input v-model="form.annualSalesNo" placeholder="请输入预估年销售" maxlength="50" type="number">
                    <el-select v-model="annualSalesUnit" slot="append" placeholder="请选择"
                               @change="changeSelectedannualSalesNo">
                      <el-option label="万" value="1"></el-option>
                      <el-option label="百万" value="2"></el-option>
                    </el-select>
                  </el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button style="margin-top: 12px" @click="onSubmit">导出</el-button>
          <el-button v-if="active===0" style="margin-top: 12px" @click="next">下一步</el-button>
          <el-button v-if="active===1" style="margin-top: 12px" @click="previous">上一步</el-button>
          <el-button v-if="active===1" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 上传文件对话框 -->
      <el-dialog :title="'上传文件'" :visible.sync="openUpload" width="fit-content" append-to-body>
        <el-upload drag class="upload-demo" ref="uploadFile" action="/stage-api/startproject/profile/avatar"
                   :on-change="handleChange"
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

      <!-- 用户导入对话框 -->
      <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
        <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                   :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                   :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip text-center" slot="tip">
            <div class="el-upload__tip" slot="tip">
              <el-checkbox v-model="upload.updateSupport"/>
              是否更新已经存在的用户数据
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline"
                     @click="importTemplate">下载模板
            </el-link>
          </div>
        </el-upload>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </el-dialog>
    </el-row>
  </div>
</template>

<style>
.el-input-group__append {
  border-left: 0;
  width: 20%;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
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
import store from "@/store";
import {parseStrEmpty, parseTime} from "@/utils/ruoyi";
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
      disabled: false,
      unit: '1',
      annualSalesUnit: '1',
      tableKey: 0,
      active: 0,
      fileList: [],
      tableLabel: [
        {label: '序号', prop: 'id'},
        {label: '场所编号', prop: 'number'},
        {label: '表头3', prop: 'a'},
        {label: '表头4', prop: 'b'},
      ],
      tableDateIndex: 0,
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
      // 用户表格数据
      userList: null,
      projectList: null,
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
        createDate: undefined,
        projectNo: '',
        projectName: '',
        header: '',
        tableData: [],
        innovation: '',
        marketPosition: '',
        innovationReq: '',
        budgetAmountNo: 0,
        annualSalesNo: 0,
        budgetAmount: 0,
        annualSales: 0
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: undefined,
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
        {key: 7, label: `负责人`, visible: true}
      ],
      // 表单校验
      rules: {
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
      JSZipUtils.getBinaryContent('/static/项目立项.docx', function(error, content) {
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
        saveAs(out, '试制记录表.docx')
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
    changeSelected(date) {
      if (date == '1') {
        this.form.budgetAmountNo = this.form.budgetAmount / 10000
      } else if (date == '2') {
        this.form.budgetAmountNo = this.form.budgetAmount / 1000000
      }
    },
    changeSelectedannualSalesNo(date) {
      if (date == '1') {
        this.form.annualSalesNo = this.form.annualSales / 10000
      } else if (date == '2') {
        this.form.annualSalesNo = this.form.annualSales / 1000000
      }
    },
    downLoad(file) {
      this.$download.resource(file, false)
    },
    next() {
      if (this.active++ > 1) this.active = 0;
    },
    previous() {
      if (this.active-- < 0) this.active = 0;
    },
    submitUpload() {
      let formDate = new FormData();
      formDate.append("avatarfile", this.fileList[0]);
      uploadFile(formDate).then(response => {
        this.form.tableData[this.tableDateIndex].file = response.Url
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
    handleUpload(row, index) {
      this.tableDateIndex = index
      this.openUpload = true;
    },
    objectSpanMethod({row, column, rowIndex, columnIndex}) {
      if (columnIndex === 0) {
        if (rowIndex === 0) {
          return {
            rowspan: 100,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      } else {
        return {
          rowspan: 1,
          colspan: 1
        };
      }
    },

    onAddItem() {
      this.form.tableData.push({
        project: "",
        projectName: "",
        executor: "",
        startDate: "",
        endDate: "",
        file: ""
      })
    },
    deleteRow(index) {

      this.form.tableData.splice(index, 1)
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true
      getProject(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        response.rows.forEach(date => {
          date.tableData = JSON.parse(date.json).tableData
          if (date.tableData == null || date.tableData == undefined || date.tableData == '') {
            date.tableData = []
          }
        })
        this.projectList = response.rows
        this.total = response.total
        this.loading = false
      })
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
        this.queryParams.id = undefined
        this.queryParams.company = data.company
      } else {
        this.queryParams.company = undefined
        this.queryParams.id = data.id
      }
      this.handleQuery()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
      this.disabled = false
    },
    onClickClose() {
      this.reset()
      this.disabled = false
    },
    // 表单重置
    reset() {
      this.active = 0
      this.form = {
        company: undefined,
        projectNo: undefined,
        projectName: undefined,
        header: undefined,
        tableData: [],
        innovation: undefined,
        marketPosition: undefined,
        innovationReq: undefined,
        budgetAmount: undefined,
        annualSales: undefined,
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
      this.queryParams.id = undefined
      this.$refs.tree.setCurrentKey(null)
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      let company = this.form.company
      this.reset()
      if (company) {
        this.form.company = company
      } else {
        this.form.company = store.getters.company
      }
      this.open = true
      this.title = "项目立项文件"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      getProjectById(row.id).then(res => {
        if (res.data.budgetAmount) {
          if (this.unit == '1') {
            res.data.budgetAmountNo = res.data.budgetAmount / 10000
          } else if (this.unit == '2') {
            res.data.budgetAmountNo = res.data.budgetAmount / 1000000
          }
        }
        if (res.data.annualSales) {
          if (this.annualSalesUnit == '1') {
            res.data.annualSalesNo = res.data.annualSales / 10000
          } else if (this.annualSalesUnit == '2') {
            res.data.annualSalesNo = res.data.annualSales / 1000000
          }
        }
        let json = JSON.parse(res.data.json)
        this.form = res.data
        this.form.tableData = json.tableData
        this.open = true

      })
      this.title = "修改项目立项文件"
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.disabled = true
      this.reset()
      getProjectById(row.id).then(res => {
        if (res.data.budgetAmount) {
          if (this.unit == '1') {
            res.data.budgetAmountNo = res.data.budgetAmount / 10000
          } else if (this.unit == '2') {
            res.data.budgetAmountNo = res.data.budgetAmount / 1000000
          }
        }
        if (res.data.annualSales) {
          if (this.annualSalesUnit == '1') {
            res.data.annualSalesNo = res.data.annualSales / 10000
          } else if (this.annualSalesUnit == '2') {
            res.data.annualSalesNo = res.data.annualSales / 1000000
          }
        }
        let json = JSON.parse(res.data.json)
        this.form = res.data
        this.form.tableData = json.tableData
        this.open = true

      })
      this.title = "查看项目立项文件"
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.open = false
          if (this.unit == '1') {
            this.form.budgetAmount = this.form.budgetAmountNo * 10000
          } else if (this.unit == '2') {
            this.form.budgetAmount = this.form.budgetAmountNo * 1000000
          }
          if (this.annualSalesUnit == '1') {
            this.form.annualSales = this.form.annualSalesNo * 10000
          } else if (this.annualSalesUnit == '2') {
            this.form.annualSales = this.form.annualSalesNo * 1000000
          }
          persistProject(this.form).then(response => {
            this.$modal.msgSuccess("新增成功")
            this.open = false
            this.getList()
          })
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
      const projectNo = row.projectNo
      this.$modal.confirm('是否确认删除项目编号为"' + projectNo + '"的数据项？').then(function () {
        return deleteProject(row)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入"
      this.upload.open = true
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

export function persistProject(data) {
  return request({
    url: '/startproject/profile/persist',
    method: 'post',
    data: data
  })
}

export function getProject(data) {
  return request({
    url: '/startproject/profile/startList',
    method: 'get',
    params: data
  })
}

export function deleteProject(data) {
  return request({
    url: '/startproject/profile/delete',
    method: 'post',
    data: data
  })
}


export function getCompanyList() {
  return request({
    url: '/startproject/profile/list',
    method: 'get'
  })
}

export function getProjectById(id) {
  return request({
    url: '/startproject/profile/getStartProjectById?id=' + parseStrEmpty(id),
    method: 'get'
  })
}

</script>
