package com.gmail.klausboeing.aws.config;

import com.amazonaws.services.s3.AmazonS3;
import com.gmail.klausboeing.aws.api.APIModule;
import com.gmail.klausboeing.aws.api.DownloadURLRequestEventHandler;
import com.gmail.klausboeing.aws.api.GetEntityEventHandler;
import com.gmail.klausboeing.aws.api.UploadURLRequestEventHandler;
import com.gmail.klausboeing.aws.application.AfterCopyEventHandler;
import com.gmail.klausboeing.aws.application.AfterUploadEventHandler;
import com.gmail.klausboeing.aws.application.ApplicationModule;
import com.gmail.klausboeing.aws.model.Repository;
import com.gmail.klausboeing.aws.model.ModelModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {APIModule.class, ProjectModule.class, ModelModule.class, ApplicationModule.class})
public interface ProjectComponent {

    AmazonS3 s3CLient();

    Repository repository();

    void inject(GetEntityEventHandler handler);

    void inject(UploadURLRequestEventHandler handler);

    void inject(AfterUploadEventHandler handler);

    void inject(AfterCopyEventHandler handler);

    void inject(DownloadURLRequestEventHandler handler);
}
