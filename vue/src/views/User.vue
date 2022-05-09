<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="Please input name: " suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input style="width: 200px" placeholder="Please input email: " suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>
      <el-input style="width: 200px" placeholder="Please input address: " suffix-icon="el-icon-position" class="ml-5" v-model="address"></el-input>
      <el-button class="ml-5" type="primary" @click="load">Search</el-button>
      <el-button type="warning" @click="reset">Reset</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">Add <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5 mr-5"
          title="Are you sure to delete all this?"
          confirm-button-text="Yes"
          cancel-button-text="No"
          icon="el-icon-info"
          icon-color="red"
          @confirm="batchDelete"
      >
        <el-button type="danger" slot="reference">Batch Deletion <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary">Import <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exportExcel" class="ml-5">Export <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBackground" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="id" label="ID" width="50">
      </el-table-column>
      <el-table-column prop="username" label="Username" width="120">
      </el-table-column>
      <el-table-column prop="nickname" label="Nickname" width="120">
      </el-table-column>
      <el-table-column prop="email" label="Email" width="140">
      </el-table-column>
      <el-table-column prop="phone" label="Phone" width="120">
      </el-table-column>
      <el-table-column prop="address" label="Address">
      </el-table-column>
      <el-table-column prop="function" label="Function">
        <template v-slot="scope">
          <el-button type="success" @click="handleEdit(scope.row)">Edit <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              title="Are you sure to delete this?"
              confirm-button-text="Yes"
              cancel-button-text="No"
              icon="el-icon-info"
              icon-color="red"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">Delete <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="User Info" :visible.sync="dialogFormVisible">
      <el-form label-width="90px" size="small">
        <!--v-model保存form的数据给后台user赋值-->
        <el-form-item label="Username">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Nickname">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Phone">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Address">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">Cancel</el-button>
              <el-button type="primary" @click="save">Confirm</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: '',
      email: '',
      address: '',
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      headerBackground: 'headerBackground',
    }
  },
  created() {
    // 请求分页查询数据
    this.load();
  },
  methods: {
    // 加载数据
    load() {
      // 请求分页查询数据
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          email: this.email,
          address: this.address,
        }
      }).then(res => {
        this.tableData = res.records;
        this.total = res.total;
      })
    },
    // 保存提交的表单数据
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res) {
          this.$message.success("Save successfully!")
          this.dialogFormVisible = false
          this.load()
        }
        else this.$message.error("Save failed!")
      })
    },
    // 新增
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    // 编辑
    handleEdit(row) {
      this.form = Object.assign({}, row)
      this.dialogFormVisible = true
    },
    // 删除
    del(id) {
      this.request.delete("/user/" + id).then(res => {
        if (res) {
          this.$message.success("Delete successfully!")
          this.load()
        }
        else this.$message.error("Delete failed!")
      })
    },
    // 获取批量删除的信息
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 批量删除
    batchDelete() {
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/user/del/batch", ids).then(res => {
        if (res) {
          this.$message.success("Batch delete successfully!")
          this.load()
        }
        else this.$message.error("Batch delete failed!")
      })
    },
    // 重置对话框
    reset() {
      this.username = ''
      this.email = ''
      this.address = ''
      this.load()
    },
    // 调pageSize对应页面变化
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    // 调pageNum对应页面变化
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
    handleExcelImportSuccess() {
      this.$message.success("Import successfully!")
      this.load()
    },
    exportExcel() {
      window.open("http://localhost:9090/user/export")
    }
  }
}
</script>

<style>
.headerBackground {
  background: #eee!important;
}
</style>