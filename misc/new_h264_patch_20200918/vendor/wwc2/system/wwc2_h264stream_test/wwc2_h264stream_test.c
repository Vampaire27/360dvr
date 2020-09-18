#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <cutils/log.h>
#include <cutils/properties.h>
#include <unistd.h>
#include <sys/types.h> 
#include <sys/un.h>
#include <sys/mman.h>

#define H264_SYNC_DEV_NAME				"/dev/wwc2_hsq_sync"
#define SHARE_MEMORY_FILE				"/sdcard/.h264StreamQuart"
#define H264_FILE_NAME					"/sdcard/streamQuart.h264"

#define H264_SYNC_DEV_FRONT_NAME		"/dev/wwc2_hsf_sync"
#define H264_SYNC_DEV_BACK_NAME			"/dev/wwc2_hsb_sync"
#define H264_SYNC_DEV_LEFT_NAME			"/dev/wwc2_hsl_sync"
#define H264_SYNC_DEV_RIGHT_NAME		"/dev/wwc2_hsr_sync"

#define SHARE_MEMORY_FRONT_FILE			"/sdcard/.h264StreamFront"
#define SHARE_MEMORY_BACK_FILE			"/sdcard/.h264StreamBack"
#define SHARE_MEMORY_LEFT_FILE			"/sdcard/.h264StreamLeft"
#define SHARE_MEMORY_RIGHT_FILE			"/sdcard/.h264StreamRight"

#define H264_FILE_FRONT_NAME			"/sdcard/streamFront.h264"
#define H264_FILE_BACK_NAME				"/sdcard/streamBack.h264"
#define H264_FILE_LEFT_NAME				"/sdcard/streamLeft.h264"
#define H264_FILE_RIGHT_NAME			"/sdcard/streamRight.h264"

union WWC2_STREAM_SIZE{
	unsigned int iSize;
	unsigned char cSize[4];
};

int main(int argc, char *argv[])
{ 
    int devfd = -1;
	int count = 0;
	union WWC2_STREAM_SIZE streamSize;
	int shmFd = -1;
	unsigned char* shm = NULL;
	char devName[32] = {0};
	char shareFileName[32] = {0};
	char saveFileName[32] = {0};
	int cameraId = 0;

	if(argc != 2)
		return -1;

	cameraId = atoi(argv[1]);
	switch(cameraId)
	{
		case 1:
			sprintf(devName, H264_SYNC_DEV_FRONT_NAME);
			sprintf(shareFileName, SHARE_MEMORY_FRONT_FILE);
			sprintf(saveFileName, H264_FILE_FRONT_NAME);
			break;

		case 2:
			sprintf(devName, H264_SYNC_DEV_BACK_NAME);
			sprintf(shareFileName, SHARE_MEMORY_BACK_FILE);
			sprintf(saveFileName, H264_FILE_BACK_NAME);
			break;

		case 3:
			sprintf(devName, H264_SYNC_DEV_LEFT_NAME);
			sprintf(shareFileName, SHARE_MEMORY_LEFT_FILE);
			sprintf(saveFileName, H264_FILE_LEFT_NAME);
			break;

		case 4:
			sprintf(devName, H264_SYNC_DEV_RIGHT_NAME);
			sprintf(shareFileName, SHARE_MEMORY_RIGHT_FILE);
			sprintf(saveFileName, H264_FILE_RIGHT_NAME);
			break;

		case 5:
			sprintf(devName, H264_SYNC_DEV_NAME);
			sprintf(shareFileName, SHARE_MEMORY_FILE);
			sprintf(saveFileName, H264_FILE_NAME);
			break;

		default:
			break;
	}

	
	FILE *fp = fopen(saveFileName, "wb");
  
	devfd = open(devName, O_RDWR);
	ALOGD("bbl--test devfd = %d",devfd);
  
    if(devfd < 0)  
    {  
		//close(devfd);
        ALOGE("bbl--ops:client");
		return 1;
    }  
 

	shmFd = open(shareFileName, O_RDWR, 0666);
	ALOGD("bbl--test shmfd = %d",shmFd);
	if(shmFd < 0)
	{
		ALOGE("bbl shmFd");
		return 2;
	}

	shm = (unsigned char*)mmap(NULL, 102400, (PROT_READ|PROT_WRITE), MAP_SHARED, shmFd, 0);
	close(shmFd);
	ALOGD("bbl--test shm = 0x%x",shm);

	while(count < 256)
	{
		read(devfd, streamSize.cSize, 4);
		ALOGD("bbl--test--%d-%d: %d", cameraId, count, streamSize.iSize);

		if(streamSize.iSize > 0)
		    fwrite(shm, 1, streamSize.iSize, fp);
		count++;
	}

	fflush(fp);
	fclose(fp);
	munmap(shm, 102400);
    close(devfd);

	return 0;
}
