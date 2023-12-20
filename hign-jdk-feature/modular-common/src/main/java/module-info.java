module modular.demo.common {
    // 声明自己对外暴露的包名
    exports com.hanmc.example.modulardemo.common;
//    exports com.hanmc.example.modulardemo.open;

//    opens com.hanmc.example.modulardemo.open to modular.demo.persistent;
//    opens 可以不用to
    opens com.hanmc.example.modulardemo.open;
    exports com.hanmc.example.modulardemo.open;
}
