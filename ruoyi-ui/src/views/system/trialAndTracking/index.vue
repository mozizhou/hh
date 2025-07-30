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
          <el-form-item label="项目名称" prop="project">
            <el-cascader
              filterable style="width: 100%"
              v-model="queryParams.project"
              :options="projectOptions"
              :props="{ expandTrigger: 'hover' }"
              @change="handleFiledChange"></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="创建日期" prop="createDate">
              <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd"
                              type="daterange" range-separator="-" start-placeholder="开始日期"
                              end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content flex-text-col">
            <el-form-item label="试制人" prop="trailHeader">
              <el-input v-model="queryParams.trailHeader" placeholder="请输入试制人姓名"/>
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

    <el-table v-loading="loading" :data="tryoutList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="项目编号" align="center" prop="projectNo"/>
      <el-table-column label="公司" align="center" prop="company"/>
      <el-table-column label="项目名称" align="center" prop="projectName"></el-table-column>
      <el-table-column label="创建时间" align="center" prop="createDate"></el-table-column>
      <el-table-column label="试制人" align="center" prop="trialProducer"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">{{ getStatusName(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="!(scope.row.status == 20004 || scope.row.status == 20005)"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >修改
          </el-button>
          <el-button
            v-if="scope.row.status != 20001"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >查看
          </el-button>
          <el-button
            v-if="scope.row.status != 20001 && !scope.row.istableData"
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleTracking(scope.$index, scope.row)"
          >问题跟踪
          </el-button>
          <el-button
            v-if="scope.row.status != 20001 && scope.row.istableData"
            style="color: green"
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleTracking(scope.$index, scope.row)"
          >问题跟踪
          </el-button>
          <el-button
            v-if="scope.row.status != 20001 && scope.row.status != 20002"
            size="mini"
            type="text"
            icon="el-icon-s-order"
            @click="handleFinalReview(scope.$index, scope.row, 20004)"
          >终审完成
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

    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body onclose="onClickClose()" v-dialogDrag
               :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :disabled="disable">
        <el-row>
          <el-col :span="24">
            <el-form-item label="项目名称" prop="projectName">
              <el-cascader
                filterable style="width: 100%"
                v-model="form.projectId"
                :options="deptOptions"
                :props="{ expandTrigger: 'hover' }"
                @change="handleChange"></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="试制数量" prop="quantity">
              <el-input type="number" v-model="form.quantity" placeholder="请输入试制数量"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位" prop="unit">
              <el-input v-model="form.unit" placeholder="请输入单位"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="设备仪器" prop="equipment">
              <el-input v-model="form.equipment" placeholder="请输入设备仪器"/>
              <!--              <el-radio-group v-model="form.status">-->
              <!--                <el-radio-->
              <!--                  v-for="dict in dict.type.sys_notice_status"-->
              <!--                  :key="dict.value"-->
              <!--                  :label="dict.value"-->
              <!--                >{{dict.label}}</el-radio>-->
              <!--              </el-radio-group>-->
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="试制情况" prop="prototypeStatus">
              <el-input type="textarea" v-model="form.prototypeStatus" placeholder="请输入试制情况"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="试制人">
              <el-input type="text" v-model="form.trialProducer" placeholder="请输入试制人"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="日期">
              <el-date-picker v-model="form.createDate" style="width: 240px" value-format="yyyy-MM-dd"
                              type="date"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onSubmit">导 出</el-button>
        <el-button v-if="form.status == 20001" type="primary" @click="startFlow">开 始</el-button>
        <el-button v-if="!disable" type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="open2" width="75%" append-to-body onclose="onClickClose()" v-dialogDrag
               :close-on-click-modal="false">
      <el-form ref="trackingForm" :model="trackingForm" :rules="rules" label-width="80px"
               :disabled="trackingForm.status != 20002">
        <el-table
          :key="key"
          :data="trackingForm.tableData"
          style="width: auto; margin-left: 30px"
          fit
        >
          <el-table-column :label="trackingForm.company">
            <el-table-column header-align=“center” align=“center” label="部门">
              <template slot="header" slot-scope="scope">
                <el-input
                  v-model="trackingForm.department"
                  size="mini"
                  placeholder="输入部门名称">
                  <template slot="prepend">部门:</template>
                </el-input>
              </template>
              <el-table-column label="序号" align="center" prop="index" width="50">
              </el-table-column>
              <el-table-column label="反馈部门" align="center" prop="feedbackDepartment" width="120">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].feedbackDepartment" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].feedbackDepartment"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="问题等级" align="center" prop="issueLevel" width="70">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].issueLevel" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].issueLevel"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="问题点描述" align="center" prop="causeAnalysis" width="150">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].causeAnalysis" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].causeAnalysis"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table-column>
          </el-table-column>
          <el-table-column :label="trackingForm.projectName + '项目方案问题跟踪表'" :align="'center'">
            <el-table-column align="center" label="填表人">
              <template slot="header" slot-scope="scope">
                <el-tooltip :content="trackingForm.fillPeople" placement="top">
                  <el-input
                    v-model="trackingForm.fillPeople"
                    size="mini"
                    placeholder="输入填表人名称">
                    <template slot="prepend">填表人:</template>
                  </el-input>
                </el-tooltip>
              </template>
              <el-table-column label="原因分析" align="center" prop="descriptionProblem" width="150">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].descriptionProblem" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].descriptionProblem"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="解决方案" align="center" prop="temporarySolution" width="150">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].temporarySolution" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].temporarySolution"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column align="center" label="审核批准">
              <el-table-column label="长期措施" align="center" prop="longtermMeasures" width="150">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].longtermMeasures" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].longtermMeasures"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column :label="trackingForm.reviewPeople">
              <el-table-column label="责任部门" align="center" prop="responsibleDepartment" width="100">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].responsibleDepartment" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].responsibleDepartment"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="责任人" align="center" prop="responsiblePerson" width="100">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].responsiblePerson" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].responsiblePerson"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="计划完成时间" align="center" prop="plannedTime" width="160">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].plannedTime" placement="top">
                    <el-date-picker
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].plannedTime"
                      value-format="yyyy-MM-dd"></el-date-picker>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="最新状态" align="center" prop="latestStatus" width="100">
                <template slot-scope="scope">
                  <el-tag :color="getStatusColor(scope.row.latestStatus)"
                          style="color: black;border: unset;font-weight: bold;">{{
                      getStatusName(scope.row.latestStatus)
                    }}
                  </el-tag>
                  <!--                  <el-select v-model="form.noticeType" placeholder="">-->
                  <!--                    <el-option-->
                  <!--                      v-for="status in statusList"-->
                  <!--                      :key="status.value"-->
                  <!--                      :label="status.label"-->
                  <!--                      :value="status.value"-->
                  <!--                    ></el-option>-->
                  <!--                  </el-select>-->
                </template>
              </el-table-column>
              <el-table-column label="实际完成时间" align="center" prop="actualTime" width="160">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].actualTime" placement="top">
                    <el-date-picker
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].actualTime"
                      value-format="yyyy-MM-dd"></el-date-picker>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table-column>
          </el-table-column>
          <el-table-column :label="'文件编号：' + trackingForm.fileNo">
            <template slot="header" slot-scope="scope">
              <el-input
                v-model="trackingForm.fileNo"
                size="mini"
                placeholder="输入文件编号">
                <template slot="prepend">文件编号:</template>
              </el-input>
            </template>
            <el-table-column>
              <el-table-column label="反馈验证人" align="center" prop="feedbackValidators" width="100">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].feedbackValidators" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].feedbackValidators"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="备注" align="center" prop="remark" width="100">
                <template #default="scope">
                  <el-tooltip :content="trackingForm.tableData[scope.$index].remark" placement="top">
                    <el-input
                      :disabled="trackingForm.tableData[scope.$index].latestStatus === '10001' || trackingForm.tableData[scope.$index].latestStatus === '10003'"
                      v-model="trackingForm.tableData[scope.$index].remark"></el-input>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column fixed="right" label="操作" min-width="120">
                <template #default="scope">
                  <el-button-group>
                    <el-button
                      :disabled="trackingForm.status != 20002 || trackingForm.tableData[scope.$index].latestStatus !== '10002'"
                      size="mini" circle type="success" icon="el-icon-check" @click="handleSuccess(scope.row)"/>
                    <el-button
                      :disabled="trackingForm.status != 20002 || trackingForm.tableData[scope.$index].latestStatus !== '10002'"
                      size="mini" type="danger" circle icon="el-icon-close" @click="handleFail(scope.row)"/>
                    <el-button
                      :disabled="trackingForm.status != 20002 || trackingForm.tableData[scope.$index].latestStatus !== '10002'"
                      v-if="trackingForm.tableData[scope.$index].latestStatus !== '10004'" size="mini" circle
                      type="primary" icon="el-icon-alarm-clock" @click="handleDelay(scope.row)"/>
                    <el-button
                      :disabled="trackingForm.status != 20002 || trackingForm.tableData[scope.$index].latestStatus !== '10004'"
                      v-if="trackingForm.tableData[scope.$index].latestStatus === '10004'" size="mini" circle
                      type="primary" icon="el-icon-caret-right" @click="handleContiune(scope.row)"/>
                    <el-button
                      :disabled="trackingForm.status != 20002 || trackingForm.tableData[scope.$index].latestStatus !== '10002'"
                      size="mini" circle type="primary" icon="el-icon-delete"
                      @click="handleTrackingDelete(scope.row, scope.$index)"/>
                  </el-button-group>
                </template>
              </el-table-column>
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
        <el-button v-if="trackingForm.status == 20002" type="primary" @click="onSubmitTracking">导 出</el-button>
        <el-button v-if="trackingForm.status == 20002" type="primary" @click="finalReview">终 审</el-button>
        <el-button v-if="trackingForm.status == 20002" type="primary" @click="submitTrackingForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="finalReviewOpen" width="50%" append-to-body :key="dateKey" v-dialogDrag
               :close-on-click-modal="false">
      <el-form ref="finalReviewForm" :model="finalReviewForm" :rules="rules" label-width="80px"
               :disabled="finalReviewForm.status != 20003">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目号" :prop="finalReviewForm.projectNo" label-width="100px">
              <el-input v-model="finalReviewForm.projectNo" placeholder="请输入项目号" maxlength="30"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称" :prop="finalReviewForm.projectName" label-width="100px">
              <el-input v-model="finalReviewForm.projectName" placeholder="请输入项目名称" maxlength="30"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="负责人" :prop="finalReviewForm.trialProducer" label-width="100px">
              <el-input v-model="finalReviewForm.trialProducer" placeholder="请输入负责人昵称" maxlength="30"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="试产数量" :prop="finalReviewForm.quantity" label-width="100px">
              <el-input v-model="finalReviewForm.quantity" placeholder="请输入试产数量" maxlength="30"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="起止日期" label-width="100px">
              <el-date-picker v-model="finalDateRange" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss"
                              type="daterange"
                              range-separator="-" start-placeholder="开始日期"
                              end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="工艺流程" :prop="finalReviewForm.process" label-width="100px">
              <el-input type="textarea" :row="3" v-model="finalReviewForm.process" placeholder="请输入产品的工艺流程"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="过程简介" :prop="finalReviewForm.overviewProcess" label-width="100px">
              <el-input type="textarea" v-model="finalReviewForm.overviewProcess"
                        placeholder="包括试产过程发现和解决的问题"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="产品检验结果" :prop="finalReviewForm.result" label-width="100px">
              <el-input type="textarea" v-model="finalReviewForm.result"
                        placeholder="试产产品检验、试验结果简介及其结论（附各阶段的检测报告记录）"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="结论" :prop="finalReviewForm.conclusionsAndRecommendations" label-width="100px">
              <el-input type="textarea" :rows="4" v-model="finalReviewForm.conclusionsAndRecommendations"
                        placeholder="试产产品检验、试验结果简介及其结论（附各阶段的检测报告记录）"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item label="项目负责人意见" :prop="finalReviewForm.headerRecommendations" label-width="100px">
              <el-input type="textarea" :rows="4" v-model="finalReviewForm.headerRecommendations"
                        placeholder="请输入产品的项目负责人意见"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目负责人" :prop="finalReviewForm.projectHeader" label-width="100px">
              <el-input v-model="finalReviewForm.projectHeader" placeholder="请输入产品的项目负责人" maxlength="30"/>
            </el-form-item>
            <el-form-item label="日期" :prop="finalReviewForm.projectHeaderDate" label-width="100px">
              <el-date-picker v-model="finalReviewForm.projectHeaderDate" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item label="总经理意见" :prop="finalReviewForm.managerRecommendations" label-width="100px">
              <el-input type="textarea" :rows="4" v-model="finalReviewForm.managerRecommendations"
                        placeholder="请输入产品的总经理意见"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="总经理" :prop="finalReviewForm.manager" label-width="100px">
              <el-input v-model="finalReviewForm.manager" placeholder="请输入产品的总经理" maxlength="30"/>
            </el-form-item>
            <el-form-item label="日期" :prop="finalReviewForm.managerDate" label-width="100px">
              <el-date-picker v-model="finalReviewForm.managerDate" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onSubmitFinal()">导 出</el-button>
        <el-button v-if="finalReviewForm.status == 20003" type="danger" @click="submitFinalForm(20005)">不通过
        </el-button>
        <el-button v-if="finalReviewForm.status == 20003" type="danger" @click="submitFinalForm(20006)">通过
        </el-button>
        <el-button v-if="finalReviewForm.status == 20003" type="success" @click="submitFinalForm(20004)">提 交
        </el-button>
        <el-button type="primary" @click="submitFinalForm(20003)">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
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
import {listNotice, getNotice, delNotice, addNotice, updateNotice} from "@/api/system/notice"
import store from "@/store";
import JSZip from 'pizzip'
import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";
import JSZipUtils from "jszip-utils";
import Docxtemplater from "docxtemplater";

export default {
  name: "Notice",
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      dateKey: 0,
      // 日期范围
      dateRange: [],
      finalDateRange: [],
      key: 0,
      deptOptions: [],
      projectOptions: [],
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
      tryoutList: [],
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
        company: undefined,
        projectName: undefined,
        projectNo: undefined,
        project: undefined,
        createDate: undefined,
        trailHeader: undefined
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
        },
        {
          label: "已通过",
          value: 20006
        }

      ],
      // 表单参数
      form: {},
      trackingForm: {},
      finalReviewForm: {},
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
      JSZipUtils.getBinaryContent('/static/试制记录表.docx', function(error, content) {
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
    onSubmitTracking() {
      this.$refs["trackingForm"].validate(valid => {
        if (valid) {
          this.exportWordTracking();
        } else {
          return false;
        }
      });
    },
    exportWordTracking() {
      const _this = this
      JSZipUtils.getBinaryContent('/static/问题跟踪.docx', function(error, content) {
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
        _this.trackingForm.tableData.map(data => {
          data.latestStatus = _this.getStatusName(data.latestStatus)
        })
        _this.trackingForm = _this.convertUndefinedToEmpty(_this.trackingForm)
        doc.setData({
          ..._this.trackingForm
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
        saveAs(out, '问题跟踪.docx')
      })
    },
    onSubmitFinal() {
      this.$refs["finalReviewForm"].validate(valid => {
        if (valid) {
          this.exportWordFinal();
        } else {
          return false;
        }
      });
    },
    exportWordFinal() {
      const _this = this
      JSZipUtils.getBinaryContent('/static/试制终审报告.docx', function(error, content) {
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
        _this.finalReviewForm = _this.convertUndefinedToEmpty(_this.finalReviewForm)
        doc.setData({
          ..._this.finalReviewForm
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
        saveAs(out, '试制终审报告.docx')
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
    handleFiledChange() {
      this.queryParams.projectNo = this.options.find(option => option.value == this.queryParams.project[1]).label
    },
    handleChange() {
      this.form.projectName = this.options.find(option => option.value == this.form.projectId[1]).label
      getTrackingByProjectId({projectId: this.form.projectId[1]}).then(res => {
        if (res.data[0]) {
          res.data[0].projectId = [res.data[0].company, Number(res.data[0].projectId)]
          this.form = res.data[0]
        }
      })
    },
    startFlow() {
      this.form.status = 20002
      this.form.company = this.form.projectId[0]
      this.form.projectId = this.form.projectId[1]
      updateTrailList(this.form).then(() => {
          this.open = false
          this.getList()
        }
      )
    },
    finalReview() {
      this.trackingForm.status = 20003
      updateTrailList(this.trackingForm).then(() => {
          this.open2 = false
          this.getList()
        }
      )
    },
    submit() {
      this.tryoutList.forEach(trial => {
        if (this.finalReviewForm.id == trial.id) {
          trial.status = 20004
          this.finalReviewOpen = false
        }
      })
    },
    backStart() {
      this.tryoutList.forEach(trial => {
        if (this.finalReviewForm.id == trial.id) {
          trial.status = 20005
          this.finalReviewOpen = false
        }
      })
    },
    handleSuccess(row) {
      this.trackingForm.tableData.forEach(data => {
        if (data.index == row.index) {
          data.latestStatus = '10001'
        }
      })
    },
    handleFail(row) {
      this.trackingForm.tableData.forEach(data => {
        if (data.index == row.index) {
          data.latestStatus = '10003'
        }
      })
    },
    handleDelay(row) {
      this.trackingForm.tableData.forEach(data => {
        if (data.index == row.index) {
          data.latestStatus = '10004'
        }
      })
    },
    handleContiune(row) {
      this.trackingForm.tableData.forEach(data => {
        if (data.index == row.index) {
          data.latestStatus = '10002'
        }
      })
    },
    handleTrackingDelete(row, index) {
      if (row.id) {
        removeTrackingById(row.id).then(() => {
          getTrackingById({id: this.trackingForm.projectId}).then(res => {
            this.$modal.msgSuccess("新增成功")
            this.trackingForm.tableData = res.data.tableData
          })
        })
      } else {
        this.trackingForm.tableData.splice(index, 1)
      }

    },
    onAddItem() {
      this.trackingForm.tableData.push({
        index: (this.trackingForm.tableData.length + 1).toString(),
        feedbackDepartment: "",
        issueLevel: "",
        causeAnalysis: "",
        descriptionProblem: "",
        temporarySolution: "",
        longtermMeasures: "",
        responsibleDepartment: "",
        responsiblePerson: "",
        plannedTime: "",
        latestStatus: "10002",
        actualTime: "",
        feedbackValidators: "",
        remark: ""
      })
    },
    onClickClose() {
      // this.reset();
      this.disable = false
    },
    /** 获取状态颜色 */
    getStatusColor(status) {
      let color
      if (status == 10001) {
        color = "#99cc00"
      } else if (status == 10002) {
        color = "#ffff00"
      } else if (status == 10003) {
        color = "#ff0000"
      } else if (status == 10004) {
        color = "#99ccff"
      } else {
        color = "#ffff00"
      }
      return color
    },
    /** 获取状态类别 */
    getStatusType(status) {
      let type
      if (status == 20001) {
        type = "info"
      } else if (status == 20002) {
        type = "warning"
      } else if (status == 20003) {
        type = ""
      } else if (status == 20004) {
        type = "success"
      } else if (status == 20005) {
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
        response.data.map(data => {
          data.projectNames.map(project => {
            project.label = project.projectNo
          })
          data.children = data.projectNames
        })
        this.projectOptions = response.data
      })
      getTrailList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tryoutList = response.rows
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
      this.form.projectId = [this.form.company, Number(this.form.projectId)]
      this.disable = false
      this.open = true
      this.title = "添加试制记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id
      getById(id).then(response => {
        response.data.projectId = [response.data.company, Number(response.data.projectId)]
        this.form = response.data
        this.open = true
        this.title = "修改试制记录"
        this.disable = false

      })
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset()
      const id = row.id
      getById(id).then(response => {
        response.data.projectId = [response.data.company, Number(response.data.projectId)]
        this.form = response.data
        this.disable = true
        this.open = true
        this.title = "查看试制记录"
      })
    },
    /** 问题跟踪按钮操作 */
    handleTracking(index, row) {
      this.reset()
      getTrackingById(row).then(res => {
        this.trackingForm = res.data
        if (this.trackingForm.tableData === null) {
          this.trackingForm.tableData = []
        }
        this.trackingForm.tableData.map((data, index) => {
          data.index = index + 1
        })
        this.open2 = true
        this.title = "问题跟踪"
      })


    },
    /** 终审按钮操作 */
    handleFinalReview(index, row, status) {
      this.reset()
      this.finalReviewForm.status = status
      console.log(row)
      console.log(this.finalReviewForm)
      this.finalReviewForm.trialid = row.id
      this.finalReviewForm.projectId=row.projectId

      // 检查 row.id 是否有效
      if (!row.id || row.id <= 0) {
        this.$message.error('试制ID无效，请检查数据');
        return;
      }

      updateFinalReportById(this.finalReviewForm).then(response => {
        this.$modal.msgSuccess("成功")
        this.finalReviewOpen = false
        this.getList()
      })



      // getFinalReportById({id: row.id}).then(res => {
      //   this.finalReviewForm = res.data
      //   this.finalDateRange = [this.finalReviewForm.createtime + ' 00:00:00', this.finalReviewForm.endtime + ' 00:00:00']
      //
      //   this.finalReviewOpen = true
      //   this.title = "终审报告"
      //   this.dateKey++
      // })

    },
    submitFinalForm(status) {
      this.$refs["finalReviewForm"].validate(valid => {
        if (valid) {
          this.finalReviewForm.status = status
          this.finalReviewForm.createtime = this.finalDateRange[0]
          this.finalReviewForm.endtime = this.finalDateRange[1]
          // 检查 this.finalReviewForm.trialId 是否有效
          console.log(this.finalReviewForm.projectId)
          if (!this.finalReviewForm.projectId || this.finalReviewForm.projectId <= 0) {
            this.$message.error('试制ID无效，请检查数据');
            return;
          }
          updateFinalReportById(this.finalReviewForm).then(response => {
            this.$modal.msgSuccess("新增成功")
            this.finalReviewOpen = false
            this.getList()
          })
        }
      })
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.id) {
            this.form.company = this.form.projectId[0]
            this.form.projectId = this.form.projectId[1]
            this.form.status = 20001
            persistTrailList(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          } else {
            this.form.company = this.form.projectId[0]
            this.form.projectId = this.form.projectId[1]
            updateTrailList(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    submitTrackingForm() {
      this.$refs["trackingForm"].validate(valid => {
        if (valid) {
          if (this.trackingForm.id != undefined && this.trackingForm.id != "") {
            console.log(this.trackingForm)
            updateTrailList(this.trackingForm).then(res => {
              this.$modal.msgSuccess("修改成功")
              this.open2 = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids
      this.$modal.confirm('是否确认删除试制记录编号为"' + id + '"的数据项？').then(function () {
        return removeById(id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    }
  }
}

export function getTrailList(data) {
  return request({
    url: '/system/production/list',
    method: 'get',
    params: data
  })
}

export function persistTrailList(data) {
  return request({
    url: '/system/production',
    method: 'post',
    data: data
  })
}

export function updateTrailList(data) {
  return request({
    url: '/system/production',
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

export function getById(id) {
  return request({
    url: '/system/production/' + parseStrEmpty(id),
    method: 'get'
  })
}


export function getTrackingByProjectId(data) {
  return request({
    url: '/system/production/listByMain',
    method: 'post',
    data: data
  })
}

export function getTrackingById(data) {
  return request({
    url: '/system/data/listByid',
    method: 'post',
    data: data
  })
}

export function getFinalReportById(data) {
  return request({
    url: '/system/productreport/listByid',
    method: 'post',
    data: data
  })
}

export function updateFinalReportById(data) {
  return request({
    url: '/system/productreport',
    method: 'put',
    data: data
  })
}

export function removeTrackingById(id) {
  return request({
    url: '/system/data/' + parseStrEmpty(id),
    method: 'delete'
  })
}

export function removeById(id) {
  return request({
    url: '/system/production/' + parseStrEmpty(id),
    method: 'delete'
  })
}
</script>
