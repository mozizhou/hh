<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-row :gutter="10" style="margin-bottom: 15px" type="flex">
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="年份" prop="year">
              <el-input
                v-model="queryParams.year"
                placeholder="请输入年份"
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
                placeholder="请输入月份"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="设备名称" prop="equipmentName">
              <el-input
                v-model="queryParams.equipmentName"
                placeholder="请输入设备名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="设备编号" prop="equipmentNo">
              <el-input v-model="queryParams.equipmentNo" placeholder="请输入设备编号"/>
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
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleImport"
            v-hasPermi="['system:notice:add']"
          >导入
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="getImportTemplate"
                     v-hasPermi="['system:user:export']">模板下载
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

    <el-table v-loading="loading" :data="equipmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" prop="id"/>
      <el-table-column label="公司" align="center" prop="company"/>
      <el-table-column label="设备名称" align="center" prop="equipmentName"></el-table-column>
      <el-table-column label="设备编号" align="center" prop="equipmentNo"></el-table-column>
      <el-table-column label="年份" align="center" prop="year"/>
      <el-table-column label="月份" align="center" prop="mouth"/>
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

    <el-dialog :title="title" :visible.sync="open" width="100%" append-to-body onclose="onClickClose()" v-dialogDrag
               :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" :disabled="disable">
        <el-table
          :data="form.tableData"
          style="width: auto; margin-left: 30px"
          fit
        >
          <el-table-column :label="'设备名称: '">
            <template slot="header" slot-scope="scope">
              <el-input
                v-model="form.equipmentName"
                size="mini"
                placeholder="输入设备名称">
                <template slot="prepend">设备名称:</template>
              </el-input>
            </template>
            <el-table-column header-align=“center” align=“center” :label="'月份'">
              <template slot="header" slot-scope="scope">
                <el-input
                  v-model="form.year"
                  size="mini"

                  placeholder="输入年份">
                  <template slot="prepend">年份:</template>
                  <template slot="append">年</template>
                </el-input>
                <el-input
                  v-model="form.mouth"
                  size="mini"
                  placeholder="输入月份">
                  <template slot="prepend">月份:</template>
                  <template slot="append">月</template>
                </el-input>
              </template>
              <el-table-column header-align=“center” align=“center” label="日期" prop="day">
                <template #default="scope">
                  <el-input v-model="form.tableData[scope.$index].day"></el-input>
                </template>
              </el-table-column>
            </el-table-column>
          </el-table-column>
          <el-table-column :label="'型号/规格: '" :align="'center'">
            <template slot="header" slot-scope="scope">
              <el-input
                v-model="form.specification"
                size="mini"
                placeholder="输入型号/规格">
                <template slot="prepend">型号/规格:</template>
              </el-input>
            </template>
            <el-table-column label="上午" align="center">
              <el-table-column label="运行时间" align="center" prop="startTimeAm">
                <template slot-scope="scope">
                  <el-time-picker
                    format="HH:mm"
                    @change="dateChange(scope.row, scope.$index, 'Am')"
                    v-model="form.tableData[scope.$index].startTimeAm"
                    placeholder="选择日期时间">
                  </el-time-picker>
                </template>
              </el-table-column>
              <el-table-column label="停止时间" align="center" prop="stopTimeAm">
                <template slot-scope="scope">
                  <el-time-picker
                    format="HH:mm"
                    v-model="form.tableData[scope.$index].stopTimeAm"
                    @change="dateChange(scope.row, scope.$index, 'Am')"
                    placeholder="选择日期时间">
                  </el-time-picker>
                </template>
              </el-table-column>
              <el-table-column label="使用时间" align="center" prop="actualTimeAm" width="100">
                <template #default="scope">
                  <el-input v-model="form.tableData[scope.$index].actualTimeAm"></el-input>
                </template>
              </el-table-column>
            </el-table-column>
          </el-table-column>
          <el-table-column :label="'设备编号: '" :align="'center'">
            <template slot="header" slot-scope="scope">
              <el-input
                v-model="form.equipmentNo"
                size="mini"
                placeholder="输入设备编号">
                <template slot="prepend">设备编号:</template>
              </el-input>
            </template>
            <el-table-column label="下午" align="center">
              <el-table-column label="运行时间" align="center" prop="startTimePm">
                <template slot-scope="scope">
                  <el-time-picker
                    v-model="form.tableData[scope.$index].startTimePm"
                    format="HH:mm"
                    @change="dateChange(scope.row, scope.$index, 'Pm')"
                    placeholder="选择日期时间">
                  </el-time-picker>
                </template>
              </el-table-column>
              <el-table-column label="停止时间" align="center" prop="stopTimePm">
                <template slot-scope="scope">
                  <el-time-picker
                    v-model="form.tableData[scope.$index].stopTimePm"
                    format="HH:mm"
                    @change="dateChange(scope.row, scope.$index, 'Pm')"
                    placeholder="选择日期时间">
                  </el-time-picker>
                </template>
              </el-table-column>
              <el-table-column label="使用时间" align="center" prop="actualTimePm" width="100">
                <template #default="scope">
                  <el-input v-model="form.tableData[scope.$index].actualTimePm"></el-input>
                </template>
              </el-table-column>
            </el-table-column>
          </el-table-column>
          <el-table-column :label="'放置地点: '" :align="'center'">
            <template slot="header" slot-scope="scope">
              <el-input
                v-model="form.place"
                size="mini"
                placeholder="输入放置地点">
                <template slot="prepend">放置地点:</template>
              </el-input>
            </template>
            <el-table-column label="设备运转情况" align="center" prop="operation" width="100">
              <template #default="scope">
                <el-input v-model="form.tableData[scope.$index].operation"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="使用部门" align="center" prop="department" width="100">
              <template #default="scope">
                <el-input v-model="form.tableData[scope.$index].department"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="使用人员" align="center" prop="users" width="100">
              <template #default="scope">
                <el-input v-model="form.tableData[scope.$index].users"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="备注" align="center" prop="mark">
              <template #default="scope">
                <el-input v-model="form.tableData[scope.$index].mark"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDeleteDay(scope.row, scope.$index)"
                  v-hasPermi="['system:notice:remove']"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table-column>
        </el-table>
        <el-row>
          <el-col :span="24" style="margin-bottom: 20px; padding-left: 30px">
            <el-button class="mt-4" style="width: 100%" @click="onAddItem">
              添加行
            </el-button>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="!disable" type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="'设备使用导入'" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess"
                 :on-error="handleFileError"
                 :action="upload.url" :disabled="upload.isUploading" :auto-upload="false" drag>
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
</style>

<script>
import store from "@/store";
import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";
import {getToken} from "@/utils/auth";
import {deepClone} from "@/utils";

export default {
  name: "Notice",
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      upload: {
        open: false,
        title: "",
        isUploading: false,
        updateSupport: 0,
        headers: {Authorization: "Bearer " + getToken()},
        url: process.env.VUE_APP_BASE_API + "/system/eqemain/importData"
      },
      dateKey: 0,
      dateRangeAm: [],
      dateRangePm: [],

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
      equipmentList: [],
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
    getImportTemplate() {
      const link = document.createElement('a');
      link.href = '/static/研发设备使用记录表25年.xlsx';
      link.download = '研发设备使用记录表25年';
      link.target = "_blank"; // 可选，如果希望在新窗口中下载文件，请取消注释此行
      link.click();
    },
    submitFileForm() {
      this.$refs.upload.submit()
    },
    dateChange(data, index, type) {
      if (type === 'Am') {
        if (this.form.tableData[index].startTimeAm && this.form.tableData[index].stopTimeAm) {
          this.form.tableData[index].actualTimeAm = ((this.form.tableData[index].stopTimeAm.getTime() - this.form.tableData[index].startTimeAm.getTime()) / 1000 / 3600).toFixed(2)
        }
      } else if (type === 'Pm') {
        if (this.form.tableData[index].startTimePm && this.form.tableData[index].stopTimePm) {
          this.form.tableData[index].actualTimePm = ((this.form.tableData[index].stopTimePm.getTime() - this.form.tableData[index].startTimePm.getTime()) / 1000 / 3600).toFixed(2)
        }
      }
    },
    onAddItem() {
      this.form.tableData.push({
        day: "",
        startTimeAm: "",
        stopTimeAm: "",
        actualTimeAm: "",
        startTimePm: "",
        stopTimePm: "",
        actualTimePm: "",
        operation: "",
        department: "",
        users: "",
        mark: "",
      })
    },
    submit() {
      this.equipmentList.forEach(trial => {
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
      getEquipmentList((this.addDateRange(this.queryParams, this.dateRange))).then(response => {
        this.equipmentList = response.rows
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
      this.form = {}
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.form.tableData = []
      this.form.company = store.getters.company
      this.disable = false
      this.open = true
      this.title = "添加设备使用记录"
    },
    handleImport() {
      this.upload.open = true
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id
      getEquipmentById(id).then(response => {
        this.form = response.data
        if (!this.form.tableData) {
          this.form.tableData = []
        }
        if (this.form.equipmentName) {
          this.form.equipmentName = this.form.equipmentName.substring(this.form.equipmentName.lastIndexOf('：') + 1);
        }
        if (this.form.specification) {
          this.form.specification = this.form.specification.substring(this.form.specification.lastIndexOf('：') + 1);
        }
        if (this.form.equipmentNo) {
          this.form.equipmentNo = this.form.equipmentNo.substring(this.form.equipmentNo.lastIndexOf('：') + 1);
        }
        if (this.form.place) {
          this.form.place = this.form.place.substring(this.form.place.lastIndexOf('：') + 1);
        }
        this.form.tableData.map(data => {
          if (data.startTimeAm) {
            data.startTimeAm = '20' + this.form.year + '-' + this.form.mouth + '-' + data.day.toString().padStart(2, '0') + ' ' + data.startTimeAm
            data.startTimeAm = new Date(data.startTimeAm)
          }
          if (data.stopTimeAm) {
            data.stopTimeAm = '20' + this.form.year + '-' + this.form.mouth + '-' + data.day.toString().padStart(2, '0') + ' ' + data.stopTimeAm
            data.stopTimeAm = new Date(data.stopTimeAm)
          }
          if (data.startTimePm) {
            data.startTimePm = '20' + this.form.year + '-' + this.form.mouth + '-' + data.day.toString().padStart(2, '0') + ' ' + data.startTimePm
            data.startTimePm = new Date(data.startTimePm)
          }
          if (data.stopTimePm) {
            data.stopTimePm = '20' + this.form.year + '-' + this.form.mouth + '-' + data.day.toString().padStart(2, '0') + ' ' + data.stopTimePm
            data.stopTimePm = new Date(data.stopTimePm)
          }
        })

        this.open = true
        this.title = "修改" + this.form.equipmentName + this.form.mouth + "使用记录"
        this.disable = false
      })
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset()
      const id = row.id
      getEquipmentById(id).then(response => {
        this.form = response.data
        if (!this.form.tableData) {
          this.form.tableData = []
        }
        if (this.form.equipmentName) {
          this.form.equipmentName = this.form.equipmentName.substring(this.form.equipmentName.lastIndexOf('：') + 1);
        }
        if (this.form.specification) {
          this.form.specification = this.form.specification.substring(this.form.specification.lastIndexOf('：') + 1);
        }
        if (this.form.equipmentNo) {
          this.form.equipmentNo = this.form.equipmentNo.substring(this.form.equipmentNo.lastIndexOf('：') + 1);
        }
        if (this.form.place) {
          this.form.place = this.form.place.substring(this.form.place.lastIndexOf('：') + 1);
        }
        this.form.tableData.map(data => {
          if (data.startTimeAm) {
            data.startTimeAm = '20' + this.form.year + '-' + this.form.mouth + '-' + data.day.toString().padStart(2, '0') + ' ' + data.startTimeAm
            let a = data.startTimeAm
            data.startTimeAm = new Date(data.startTimeAm)
          }
          if (data.stopTimeAm) {
            data.stopTimeAm = '20' + this.form.year + '-' + this.form.mouth + '-' + data.day.toString().padStart(2, '0') + ' ' + data.stopTimeAm
            data.stopTimeAm = new Date(data.stopTimeAm)
          }
          if (data.startTimePm) {
            data.startTimePm = '20' + this.form.year + '-' + this.form.mouth + '-' + data.day.toString().padStart(2, '0') + ' ' + data.startTimePm
            data.startTimePm = new Date(data.startTimePm)
          }
          if (data.stopTimePm) {
            data.stopTimePm = '20' + this.form.year + '-' + this.form.mouth + '-' + data.day.toString().padStart(2, '0') + ' ' + data.stopTimePm
            data.stopTimePm = new Date(data.stopTimePm)
          }
        })
        this.disable = true
        this.open = true
        this.title = "查看" + this.form.equipmentName + this.form.mouth + "使用记录"
      })
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        console.log(this.form.tableData)
        if (this.form.tableData) {
          this.form.tableData.forEach(data => {
            let startTimeAm = data.startTimeAm
            let hours1 = startTimeAm.getHours().toString().padStart(2, '0');
            let minutes1 = startTimeAm.getMinutes().toString().padStart(2, '0');
            let seconds1 = startTimeAm.getSeconds().toString().padStart(2, '0');
            data.startTimeAm = `${hours1}:${minutes1}:${seconds1}`
            let stopTimeAm = data.stopTimeAm
            let hours2 = stopTimeAm.getHours().toString().padStart(2, '0');
            let minutes2 = stopTimeAm.getMinutes().toString().padStart(2, '0');
            let seconds2 = stopTimeAm.getSeconds().toString().padStart(2, '0');
            data.stopTimeAm = `${hours2}:${minutes2}:${seconds2}`
            let startTimePm = data.startTimePm
            let hours3 = startTimePm.getHours().toString().padStart(2, '0');
            let minutes3 = startTimePm.getMinutes().toString().padStart(2, '0');
            let seconds3 = startTimePm.getSeconds().toString().padStart(2, '0');
            data.startTimePm = `${hours3}:${minutes3}:${seconds3}`
            let stopTimePm = data.stopTimePm
            let hours4 = stopTimePm.getHours().toString().padStart(2, '0');
            let minutes4 = stopTimePm.getMinutes().toString().padStart(2, '0');
            let seconds4 = stopTimePm.getSeconds().toString().padStart(2, '0');
            data.stopTimePm = `${hours4}:${minutes4}:${seconds4}`

          })
        }

        if (valid) {
          if (this.form.id != undefined && this.form.id != "") {
            updateEquipmentList(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            insertEquipmentList(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除设备使用记录记录编号为"' + id + '"的数据项？').then(function () {
        return removeEquipmentList(id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 删除记录操作 */
    handleDeleteDay(row, index) {
      const id = row.id
      const formId = this.form.id
      if (id) {
        this.$modal.confirm('是否确认删除设备使用记录记录编号为"' + id + '"的数据项？').then(function () {
          return deleteEquipmentList(id)
        }).then(() => {
          getEquipmentById(formId).then(response => {
            this.form = response.data
          })
          this.$modal.msgSuccess("删除成功")
        }).catch(() => {
        })
      } else {
        this.form.tableData.splice(index, 1)
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
    // 文件上传成功处理
    handleFileError(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true})
      this.getList()
    }
  }
}

export function getEquipmentList(data) {
  return request({
    url: '/system/eqemain/list',
    method: 'get',
    params: data
  })
}

export function insertEquipmentList(data) {
  return request({
    url: '/system/eqemain',
    method: 'post',
    data: data
  })
}

export function updateEquipmentList(data) {
  return request({
    url: '/system/eqemain',
    method: 'put',
    data: data
  })
}

export function removeEquipmentList(id) {
  return request({
    url: '/system/eqemain/' + parseStrEmpty(id),
    method: 'delete'
  })
}

export function deleteEquipmentList(id) {
  return request({
    url: '/system/usage/' + parseStrEmpty(id),
    method: 'delete'
  })
}

export function getCompanyList() {
  return request({
    url: '/startproject/profile/list',
    method: 'get'
  })
}

export function getEquipmentById(id) {
  return request({
    url: '/system/eqemain/' + parseStrEmpty(id),
    method: 'get'
  })
}

</script>
