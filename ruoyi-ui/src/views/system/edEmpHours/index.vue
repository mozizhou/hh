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
            <el-form-item label="年份" prop="year">
              <el-input
                v-model="queryParams.year"
                placeholder="请输入项目名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="月份" prop="mouth">
              <el-input
                v-model="queryParams.mouth"
                placeholder="请输入项目名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10" type="flex" class="mb8" style="margin-bottom: 15px">
        <el-col :span="1.5">
          <el-popover
            placement="bottom"
            width="250"
            trigger="hover"
            icon="el-icon-download">
            <p>请选择要导入的模板格式。</p>
            <el-col :span="12">
              <div style="text-align: left; margin: 0">
                <el-button size="mini" type="primary" @click="handleImport('month')">月度</el-button>
              </div>
            </el-col>
            <el-col :span="12">
              <div style="text-align: right; margin: 0">
                <el-button type="primary" size="mini" @click="handleImport('day')">日期</el-button>
              </div>
            </el-col>
            <el-button type="info" plain icon="el-icon-upload2" size="mini" slot="reference">导入</el-button>
          </el-popover>
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                     v-hasPermi="['system:user:export']">导出
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-popover
            placement="bottom"
            width="250"
            trigger="hover"
            icon="el-icon-download">
            <p>请选择要下载的模板格式。</p>
            <el-col :span="12">
              <div style="text-align: left; margin: 0">
                <el-button size="mini" type="primary" @click="getMonthImportTemplate">月度</el-button>
              </div>
            </el-col>
            <el-col :span="12">
              <div style="text-align: right; margin: 0">
                <el-button type="primary" size="mini" @click="getDayImportTemplate">日期</el-button>
              </div>
            </el-col>
            <el-button slot="reference" type="warning" plain icon="el-icon-download" size="mini">模板下载</el-button>
          </el-popover>
          <!--          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="getImportTemplate"-->
          <!--                     v-hasPermi="['system:user:export']">模板下载-->
          <!--          </el-button>-->
        </el-col>
        <el-col :span="1.5">
          <el-button :disabled="ids.length === 0" type="danger" plain icon="el-icon-download" size="mini" @click="handleDelete"
                     v-hasPermi="['system:user:export']">删除
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

    <el-table
      :data="tableData"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      lazy
      :key="tableKey"
      :load="load"
      :row-class-name="tableRowClassName"
      @selection-change="handleSelectionChange"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
        type="selection"
        :selectable="checkSelectable"
        width="55">
      </el-table-column>
      <el-table-column
        prop="year"
        label="年份"
        sortable>
      </el-table-column>
      <el-table-column
        prop="mouth"
        label="月份"
        sortable>
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" v-model="scope.row.mouth" placeholder="请输入项目名称" maxlength="50"/>
          <div v-else-if="!scope.row.isEdit">
            {{ scope.row.mouth }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="projectName"
        label="项目名称"
        sortable>
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" v-model="scope.row.projectName" placeholder="请输入项目名称" maxlength="50"/>
          <div v-else-if="!scope.row.isEdit">
            {{ scope.row.projectName }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="empName"
        label="人员名称"
        sortable>
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" v-model="scope.row.empName" placeholder="请输入项目名称"
                    maxlength="50"/>
          <div v-else-if="!scope.row.isEdit">
            {{ scope.row.empName }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="monthWorkHours"
        label="月工作总时间"
        sortable>
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" v-model="scope.row.monthWorkHours" placeholder="请输入项目名称" maxlength="50"/>
          <div v-else-if="!scope.row.isEdit">
            {{ scope.row.monthWorkHours }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="projectWorkHours"
        label="项目维度工作时间">
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" v-model="scope.row.projectWorkHours" placeholder="请输入项目名称" maxlength="50"/>
          <div v-else-if="!scope.row.isEdit">
            {{ scope.row.projectWorkHours }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="jobPost"
        label="从事岗位">
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" v-model="scope.row.jobPost" placeholder="请输入项目名称" maxlength="50"/>
          <div v-else-if="!scope.row.isEdit">
            {{ scope.row.jobPost }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="price"
        label="单价"
        sortable>
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" v-model="scope.row.price" placeholder="请输入项目名称" maxlength="50"/>
          <div v-else-if="!scope.row.isEdit && scope.row.price">
            {{ Number(scope.row.price).toFixed(5) }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="empType"
        label="人员类型">
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" v-model="scope.row.empType" placeholder="请输入项目名称"
                    maxlength="50"/>
          <div v-else-if="!scope.row.isEdit">
            {{ scope.row.empType }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.mouth && !scope.row.projectName"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >查看
          </el-button>
          <el-button
            v-if="scope.row.days && scope.row.isEdit === false"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)"
            v-hasPermi="['system:material:edit']"
          >编辑
          </el-button>
          <el-button
            v-if="scope.row.days && scope.row.isEdit === true"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleSave(scope.row)"
            v-hasPermi="['system:material:edit']"
          >保存
          </el-button>
          <el-button
            v-if="scope.row.days"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:material:edit']"
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

    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body onclose="onClickClose()" v-dialogDrag
               :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" :disabled="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司" prop="companyName">
              <el-input v-model="form.companyName"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="年份" prop="yearOfDate">
              <el-input v-model="form.yearOfDate"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="月份" prop="mouth">
              <el-input v-model="form.mouth"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-col v-for="(child, index) in form.children" :key="index">
          <el-table
            :data="child.children"
            :span-method="objectSpanMethod"
            style="width: auto; margin-left: 30px"
            show-summary
            :summary-method="getSummaries"
            border
          >
            <el-table-column label="项目名称">
              {{ child.projectName }}
            </el-table-column>
            <el-table-column
              label="日期"
              width="200" prop="days">
            </el-table-column>
            <el-table-column
              label="材料名称"
              width="200" prop="name">
            </el-table-column>
            <el-table-column
              label="单位" prop="unit">
            </el-table-column>
            <el-table-column
              label="数量" prop="total">
            </el-table-column>
            <el-table-column
              label="单价(元)" prop="price">
            </el-table-column>
            <el-table-column
              label="总价(元)" prop="totalAmount">
            </el-table-column>
          </el-table>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="'材料领用导入'" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :action="upload.url + '?updateSupport=false'" :disabled="upload.isUploading"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
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

.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>

<script>
import store from "@/store";
import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";
import {getToken} from "@/utils/auth";

export default {
  name: "Notice",
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      tableKey: 0,
      temp: {},
      temp2: {},
      temp3: {},
      temp4: {},
      data: {},
      data2: {},
      data3: {},
      data4: {},
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
        url: process.env.VUE_APP_BASE_API + "/system/summary/importData"
      },
      treeIndex: 1,
      tableData: [],
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
        year: '',
        mouth: ''
      },
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
    checkSelectable(row) {
      console.log(row)
      return row.days
    },
    getMonthImportTemplate() {
      const link = document.createElement('a');
      link.href = '/static/材料领用月份模板.xlsx'
      link.download = '月度材料领用'
      link.target = "_blank"
      link.click();
    },
    getDayImportTemplate() {
      const link = document.createElement('a');
      link.href = '/static/材料领用日期模板.xlsx'
      link.download = '日期材料领用'
      link.target = "_blank"
      link.click();
    },
    load(tree, treeNode, resolve) {
      this.temp4 = this.temp3
      this.temp3 = this.temp2
      this.temp2 = this.temp
      this.temp = resolve
      this.treeIndex = this.treeIndex + 1000000000
      let data = {year: undefined, company: undefined, mouth: undefined, projectName: undefined}
      data.year = tree.year
      data.company = tree.company
      data.mouth = tree.mouth
      data.projectName = tree.projectName
      this.data4 = this.data3
      this.data3 = this.data2
      this.data2 = this.data
      this.data = data
      getSummary(data).then(res => {
        res.rows.map((row, index) => {
          if (!row.id) {
            row.id = index + this.treeIndex
          }
          row.isEdit = false
        })
        resolve(res.rows)
      })
    },
    reload(data) {
      getSummary(data).then(res => {
        res.rows.map((row, index) => {
          if (!row.id) {
            row.id = index + this.treeIndex
          }
          row.isEdit = false
        })
        if (res.total == 0) {
          this.getList()
        } else {
          this.temp(res.rows)

        }
      })
    },
    reloadFather(data) {
      getSummary(data).then(res => {
        res.rows.map((row, index) => {
          if (!row.id) {
            row.id = index + this.treeIndex
          }
          row.isEdit = false
        })
        this.temp2(res.rows)
      })
    },
    reloadGFather(data) {
      getSummary(data).then(res => {
        res.rows.map((row, index) => {
          if (!row.id) {
            row.id = index + this.treeIndex
          }
          row.isEdit = false
        })
        this.temp3(res.rows)
      })
    },
    reloadAll(data) {
      getSummary(data).then(res => {
        res.rows.map((row, index) => {
          if (!row.id) {
            row.id = index + this.treeIndex
          }
          row.isEdit = false
        })
        this.temp4(res.rows)
      })
    },
    handleExport() {
      this.download('system/summary/export', {
        ...this.queryParams
      }, `物料领用_${new Date().getTime()}.xlsx`)
    },
    getSummaries(param) {
      const {columns, data} = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计';
          return;
        }
        if (index === 1) {
          sums[index] = '';
          return;
        }
        if (index === 2) {
          sums[index] = '';
          return;
        }
        if (index === 5) {
          sums[index] = '';
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
          sums[index] += '';
        } else {
          sums[index] = '';
        }
      });

      sums.map((sum, index) => {
        if (index === 4 || index === 6) {
          sums[index] = Number(sum).toFixed(2)
        }
      })

      return sums;
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
    tableRowClassName({row, rowIndex}) {
      if (row.name) {
        return 'success-row';
      } else if (row.projectName) {
        return 'warning-row';
      }
      return '';
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
      this.treeIndex = 1
      getSummary(this.queryParams).then(response => {
        this.tableData = []
        this.tableData = response.rows
        this.tableData.map((data, index) => {
          data.id = this.treeIndex + 1
          this.treeIndex = this.treeIndex + 1
        })
        this.total = response.total
        this.loading = false
        this.tableKey++
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
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset()
      const data = {company: row.company, year: row.year, mouth: row.mouth}
      viewData(data).then(response => {
        const dataMap = response.data;
        const groupKeys = Object.keys(dataMap);
        let companyName
        let yearOfDate
        let mouth
        this.form.children = []
        groupKeys.forEach(groupKey => {
          const items = dataMap[groupKey];
          this.form.children.push(items)
          items.forEach(item => {
            companyName = item.company
            yearOfDate = item.year
            mouth = item.mouth
          });
        });
        this.form.children.map(child => {
          child.children = child
        })
        this.form.companyName = companyName
        this.form.yearOfDate = yearOfDate
        this.form.mouth = mouth
        this.form.children.map(child => {
          child.map((data, index) => {
            data.price = data.price.toFixed(5)
          })
        })
        this.open = true
        this.title = "查看物料清单"
      })
    },
    /** 查看按钮操作 */
    handlePush(row) {
      row.children.push({
        id: '',
        date: '',
        company: '',
        total: 0,
        totalAmount: 0,
        isEdit: true,
        projectName: '',
        name: '',
        price: 0,
      })
    },
    handleEdit(row) {
      row.isEdit = true
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined && this.form.id != "") {
            this.$modal.msgSuccess("修改成功")
          } else {
            this.form.company = store.getters.company
            this.form.id = this.reportList.length + 1
            this.reportList.push(this.form)
            this.$modal.msgSuccess("新增成功")
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids
      this.$modal.confirm('是否确认删除物料记录记录记录编号为"' + id + '"的数据项？').then(function () {
        return deleteData(id)
      }).then(() => {
        this.reload(this.data)
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 删除按钮操作 */
    handleSave(row) {
      editData(row).then(response => {
        row.isEdit = false
        this.reloadFather(this.data2)
      })
    },

    /** 导入按钮操作 */
    handleImport(type) {
      this.upload.open = true
      if (type == 'month') {
        this.upload.url = process.env.VUE_APP_BASE_API + "/system/summary/importDataBymonth"
      } else if (type == 'day') {
        this.upload.url = process.env.VUE_APP_BASE_API + "/system/summary/importData"
      }
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
    },
    getImportTemplate() {
      const link = document.createElement('a');
      link.href = '/static/材料领用日期模板.xlsx'
      link.download = '日期材料领用'
      link.target = "_blank"
      link.click();
    }
  }
}

export function getImportTemplate() {
  return request({
    url: '/system/summary/importTemplate',
    method: 'get'
  })
}

export function getSummary(data) {
  return request({
    url: '/system/summary/list',
    method: 'post',
    data: data
  })
}

export function importData() {
  return request({
    url: '/system/summary/importData',
    method: 'get'
  })
}

export function exportData(data) {
  return request({
    url: '/system/summary/export',
    method: 'post',
    data: data
  })
}

export function viewData(data) {
  return request({
    url: '/system/summary/view',
    method: 'post',
    data: data
  })
}

export function editData(data) {
  return request({
    url: '/system/summary',
    method: 'put',
    data: data
  })
}

export function deleteData(ids) {
  return request({
    url: '/system/summary/' + parseStrEmpty(ids),
    method: 'delete'
  })
}
</script>
